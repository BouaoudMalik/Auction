package components.threshold;

import java.util.ArrayList;

import java.util.Map;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.Vector;

import components.threshold.connections.connectors.ThresholdBidderConnector;
import components.threshold.connections.connectors.ThresholdSellerConnector;
import components.threshold.connections.obp.ThresholdBidderOutboundPort;
import components.threshold.connections.obp.ThresholdSellerOutboundPort;
import components.threshold.interfaces.ThresholdBidderCI;
import components.threshold.interfaces.ThresholdSellerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import entities.ProtocolProgress;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import plugins.threshold.ThresholdAuctioneerPlugin;

/**
 * Class that represent a threshold auctioneer component, this class is supposed
 * to manage an auction of an threshold protocol bidding
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
@RequiredInterfaces(required = { ThresholdSellerCI.class, ThresholdBidderCI.class })
public class ThresholdAuctioneer extends AbstractComponent {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected ThresholdSellerOutboundPort sellerOutboundPort;

	private Vector<BiddedObject> biddedObjectList;
	private Vector<ThresholdBidder> bidders;
	private ArrayList<ThresholdBidderOutboundPort> biddersOBP;
	private ArrayList<String> biddersIBP;

	private static int currentParticipants = 0;
	private int maxAutorizedBidder;
	@SuppressWarnings("unused")
	private boolean turn = false;

	private Semaphore lock = new Semaphore(1);
	private int numberOfThreads;

	private static final String PROTOCOLE_POOL_URI_LAUNCH = AbstractPort.generatePortURI();

	// Plugin
	protected ThresholdAuctioneerPlugin thresholdAuctioneerPlugin;
	public String auctioneer_plugin_URI;
	public String seller_plugin_URI;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	protected ThresholdAuctioneer(int maxAutorizedBidder, ArrayList<ThresholdBidderOutboundPort> potentialBidders,
			ArrayList<String> biddersIBP, int numberOfThreads, String auctioneer_plugin_URI, String seller_plugin_URI)
			throws Exception {
		super(2, 1);

		this.biddersIBP = biddersIBP;
		this.maxAutorizedBidder = maxAutorizedBidder;

		this.biddedObjectList = new Vector<BiddedObject>();
		this.bidders = new Vector<ThresholdBidder>();

		// Plugin auctioneer
		this.thresholdAuctioneerPlugin = new ThresholdAuctioneerPlugin(auctioneer_plugin_URI);
		this.thresholdAuctioneerPlugin.setPluginURI(auctioneer_plugin_URI + AbstractPort.generatePortURI());
		this.installPlugin(this.thresholdAuctioneerPlugin);
		this.seller_plugin_URI = seller_plugin_URI;
		this.sellerOutboundPort = new ThresholdSellerOutboundPort(this);
		this.sellerOutboundPort.publishPort();

		this.biddersOBP = potentialBidders;
		// For each IBP, we create an OBP
		for (int i = 0; i < this.biddersIBP.size(); i++) {
			ThresholdBidderOutboundPort bidderOuboundPort = new ThresholdBidderOutboundPort(this);
			this.biddersOBP.add(bidderOuboundPort);
			bidderOuboundPort.publishPort();
		}

		this.numberOfThreads = numberOfThreads;
		this.createNewExecutorService(PROTOCOLE_POOL_URI_LAUNCH, this.numberOfThreads, false);
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	@Override
	public synchronized void start() throws ComponentStartException {
		try {
			this.doPortConnection(this.sellerOutboundPort.getPortURI(), this.seller_plugin_URI,
					ThresholdSellerConnector.class.getCanonicalName());
			// Connecting the OBPs with IBPs URis
			for (int i = 0; i < this.biddersOBP.size(); i++) {
				this.doPortConnection(this.biddersOBP.get(i).getPortURI(), this.biddersIBP.get(i),
						ThresholdBidderConnector.class.getCanonicalName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.start();
	}

	@Override
	public synchronized void execute() throws Exception {
		super.execute();

		for (BiddedObject biddedObject : sellerOutboundPort.getListOfGoods()) {
			this.runTask(PROTOCOLE_POOL_URI_LAUNCH, a -> {
				try {
					ProtocolState state = new ProtocolState(ProtocolProgress.Init, biddedObject,
							new ConcurrentHashMap<String, Float>());
					launchProtocol(state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public synchronized void finalise() throws Exception {
		super.finalise();

		this.doPortDisconnection(this.sellerOutboundPort.getPortURI());
		for (int i = 0; i < this.biddersOBP.size(); i++) {
			this.doPortDisconnection(this.biddersOBP.get(i).getPortURI());
		}
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.sellerOutboundPort.unpublishPort();
			for (int i = 0; i < this.biddersOBP.size(); i++) {
				this.biddersOBP.get(i).unpublishPort();
			}
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method checking if the selling request is accepted or not
	 * 
	 * @param object the bid object for the auction
	 * @return boolean value
	 * @throws Exception throw an exception
	 */
	public boolean acceptSellingRequest(BiddedObject object) throws Exception {
		if (object != null) {
			System.out.println("The selling request has been received and accepted.");
			this.addObjectToBid(object);
			return true;
		}
		return false;
	}

	/**
	 * Method verifying and add bidders to the list of participation request for an
	 * threshold bidding
	 * 
	 * @param bidder the bidder
	 * @return boolean value
	 * @throws Exception throw an exception
	 */
	public boolean acceptParticipationRequest(ThresholdBidder bidder) throws Exception {
		if (ThresholdAuctioneer.currentParticipants < this.maxAutorizedBidder) {
			ThresholdAuctioneer.currentParticipants++;
			this.bidders.add(bidder);
			return true;
		}
		return false;
	}

	/**
	 * Method adding the offer in the hash map with the id of the bidder associated
	 * with the price offered
	 * 
	 * @param offers    the offers hash map
	 * @param offerMade the offer made by a bidder
	 */
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offerMade) {
		offers.put(offerMade.getBidderId(), offerMade.getPriceOffer());
	}

	/**
	 * Method adding an object for an auction
	 * 
	 * @param object the bid object for the auction
	 */
	public void addObjectToBid(BiddedObject object) {
		this.biddedObjectList.add(object);
	}

	/**
	 * Method getting the winner by searching the max price in the hash map
	 * 
	 * @param offers the offers hash map
	 * @return the max price
	 */
	public Entry<String, Float> getBidderWinner(ConcurrentHashMap<String, Float> offers) {
		assert offers.size() > 0;
		Map.Entry<String, Float> maxentry = null;
		for (Map.Entry<String, Float> entry : offers.entrySet()) {
			if (maxentry == null || entry.getValue().compareTo(maxentry.getValue()) > 0) {
				maxentry = entry;
			}
		}
		return maxentry;
	}

	/**
	 * Method returning a list of potential bidders
	 * 
	 * @return array list of potential bidders
	 */
	private ArrayList<ThresholdBidderOutboundPort> getPotentialBidders() {
		return this.biddersOBP;
	}

	/**
	 * Method getting the list of bidders participating in the threshold bidding
	 * 
	 * @return array list of bidders
	 */
	public Vector<ThresholdBidder> getBidders() {
		return bidders;
	}

	/****************************************************************************
	 * * * * * THRESHOLD PROTOCOL IMPLEMENTATION * * * * *
	 ****************************************************************************/

	/**
	 * Method initializing the threshold auction
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from an outbound port
	 */
	public void initializeBid(ProtocolState state) throws Exception {
		if (state.getState() == ProtocolProgress.Init) {
			boolean responseToSellingRequest = false;
			// on suppose qu'on met en enchère le premier objet de la liste
			// requete acceptée
			// rajouter le bien à la liste des enchères
			responseToSellingRequest = sellerOutboundPort.sellingRequest(state.getBiddedObject());
			if (responseToSellingRequest) {
				state.setState(ProtocolProgress.PermissionToParticipate);
			}
		}
	}

	/**
	 * Method adding participants to the auction
	 * 
	 * @param state the protocol state
	 */
	public void addParticipants(ProtocolState state) {
		if (state.getState() == ProtocolProgress.PermissionToParticipate) {
			for (int i = 0; i < this.getPotentialBidders().size(); i++) {
				try {
					if (state.getBiddedObject().getObjectId() == this.getPotentialBidders().get(i)
							.getObjectInterestedIn().getObjectId())
						getPotentialBidders().get(i).participationRequest();
					if (i == this.getPotentialBidders().size() - 1) {
						state.setState(ProtocolProgress.Bidding);
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Launch the threshold protocol
	 * 
	 * @param state the protocol state at the launch
	 * @throws Exception when calling a method from an outbound port
	 */
	public void launchProtocol(ProtocolState state) throws Exception {
		this.initializeBid(state);
		System.out.println("The auction id {" + state.getBiddedObject().getObjectId() + "}, "
				+ state.getBiddedObject().getDescription() + " start \n\t---");
		this.addParticipants(state);

		this.auction(state);

		closeBid(state);
	}

	/**
	 * Method with the bidder action when he is interested by the auction
	 * 
	 * @param state  the state of the protocol
	 * @param bidder the bidder
	 * @param offer  the offer made by the bidder
	 */
	private void bidderAction(ProtocolState state, ThresholdBidder bidder, Offer offer) {
		try {
			if (!bidder.getHasMadeAnOffer()) {
				envelopeInformations(state, bidder, offer);
				this.addOffer(state.getOffers(), offer);
				bidder.setHasMadeAnOffer(true);
				Thread.sleep(1000);
			}
			this.lock.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method containing informations about an envelope
	 * 
	 * @param state  the state of the protocol
	 * @param bidder the bidder
	 * @param offer  the offer made by the bidder
	 */
	private void envelopeInformations(ProtocolState state, ThresholdBidder bidder, Offer offer) {
		System.out.println("Closed envelope of " + bidder.bidderId + " for {" + state.getBiddedObject().getObjectId()
				+ "} with the amount : " + offer.getPriceOffer() + "€");
	}

	/**
	 * Method conducting a threshold auction
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from outbound port
	 */
	public void auction(ProtocolState state) throws Exception {
		if (state.getState() == ProtocolProgress.Bidding) {
			for (int i = 0; i < this.getBidders().size(); i++) {
				this.lock.release();
				this.lock.acquire();
				ThresholdBidder bidder = this.getBidders().get(i);
				boolean bidderInterestedInAuction = state.getBiddedObject().getObjectId() == bidder
						.getObjectInterestedIn().getObjectId();
				if (bidderInterestedInAuction) {
					Offer offer = bidder.makeAnOffer(state.getBiddedObject());
					bidderAction(state, bidder, offer);
				}
			}
			state.setState(ProtocolProgress.OpenEnvelopes);
			this.turn = true;
		}
	}

	/**
	 * Method closing the threshold bidding
	 * 
	 * @param state the protocol state
	 * @throws Exception when using semaphore
	 */
	public void closeBid(ProtocolState state) throws Exception {
		if (state.getState() == ProtocolProgress.OpenEnvelopes) {
			this.lock.acquire();
			announceWinner(state);
			notifySeller(state);
			if (state.getState() == ProtocolProgress.PreparingClosure) {
				System.out.println("Ranking {" + state.getBiddedObject().getObjectId() + "}: "
						+ state.sortOffers(state.getOffers()));
			}
			System.out.println("The threshold auction {" + state.getBiddedObject().getObjectId()
					+ "} is closed. \nThanks for participating.");
			state.setState(ProtocolProgress.Closed);
		}
	}

	/**
	 * Retrieve the winner of the auction
	 * 
	 * @param state the protocol state
	 */
	public void announceWinner(ProtocolState state) {
		if (state.getOffers().size() > 0) {
			Entry<String, Float> winnerReferences = this.getBidderWinner(state.getOffers());
			System.out.println("The winner of " + state.getBiddedObject().getDescription() + " in the auction {"
					+ state.getBiddedObject().getObjectId() + "} is : '" + winnerReferences.getKey()
					+ "' and made an offer of " + winnerReferences.getValue() + "€");
		} else {
			System.out.println("No one was interested by this auction");
		}
		state.setState(ProtocolProgress.NotifySeller);
	}

	/**
	 * Method notifying the seller when their object has been sold
	 * 
	 * @param state the protocol state
	 */
	public void notifySeller(ProtocolState state) {
		state.getBiddedObject().setSelled(true);
		System.out.println("The seller of the threshold bidding has been notified.");
		state.setState(ProtocolProgress.PreparingClosure);
	}

}
