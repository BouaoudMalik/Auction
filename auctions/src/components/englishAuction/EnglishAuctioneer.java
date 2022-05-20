package components.englishAuction;

import components.englishAuction.interfaces.EnglishBidderCI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import components.englishAuction.connections.connectors.EnglishBidderConnector;
import components.englishAuction.connections.connectors.EnglishSellerConnector;
import components.englishAuction.connections.obp.EnglishBidderOutboundPort;
import components.englishAuction.connections.obp.EnglishSellerOutboundPort;

import components.englishAuction.interfaces.EnglishSellerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolProgress;
import entities.ProtocolState;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import plugins.englishAuction.EnglishAuctioneerPlugin;
import plugins.englishAuction.EnglishSellerPlugin;

import java.util.concurrent.*;

/**
 * Class that represent an English auctioneer component, this class is supposed
 * to manage an auction of an English protocol bidding
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
@RequiredInterfaces(required = { EnglishSellerCI.class, EnglishBidderCI.class })
public class EnglishAuctioneer extends AbstractComponent {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected EnglishSellerOutboundPort englishSellerOutboundPort;
	protected EnglishBidderOutboundPort englishBidderOutboundPort;

	// Plugin
	protected EnglishAuctioneerPlugin englishAuctioneerPlugin;
	/**
	 * auctionner plugin uri
	 */
	public String auctioneer_plugin_URI = "auctioneer-plugin-uri";

	private int currentParticipants = 0;
	private int maxAutorizedBidder;
	private float currentPriceReference;

	private ArrayList<EnglishBidderOutboundPort> biddersOBP;
	private ArrayList<String> biddersIBP;
	private Vector<BiddedObject> biddedObjectList;
	private Vector<EnglishBidder> bidders;

	private Semaphore lock = new Semaphore(1);
	private int numberOfThreads;
	private static final String PROTOCOLE_POOL_URI_LAUNCH = AbstractPort.generatePortURI();

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	protected EnglishAuctioneer(ArrayList<EnglishBidderOutboundPort> potentialBidders, ArrayList<String> biddersIBP,
			int maxAutorizedBidder, int numberOfThreads) throws Exception {
		super(2, 1);

		this.biddersIBP = biddersIBP;
		this.numberOfThreads = numberOfThreads;
		this.englishSellerOutboundPort = new EnglishSellerOutboundPort(this);
		this.englishSellerOutboundPort.publishPort();

		// Plugin auctioneer
		this.englishAuctioneerPlugin = new EnglishAuctioneerPlugin();
		this.englishAuctioneerPlugin.setPluginURI(auctioneer_plugin_URI + AbstractPort.generatePortURI());
		this.installPlugin(englishAuctioneerPlugin);

		this.biddersOBP = potentialBidders;
		// For each IBP, we create an OBP
		for (int i = 0; i < this.biddersIBP.size(); i++) {
			EnglishBidderOutboundPort bidderOuboundPort = new EnglishBidderOutboundPort(this);
			this.biddersOBP.add(bidderOuboundPort);
			bidderOuboundPort.publishPort();
		}

		this.maxAutorizedBidder = maxAutorizedBidder;

		this.biddedObjectList = new Vector<BiddedObject>();
		this.bidders = new Vector<EnglishBidder>();

		this.createNewExecutorService(PROTOCOLE_POOL_URI_LAUNCH, numberOfThreads, false);
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();
		try {
			this.doPortConnection(this.englishSellerOutboundPort.getPortURI(),
					EnglishSellerPlugin.englishSellerPlugin_URI, EnglishSellerConnector.class.getCanonicalName());
			// Connecting the OBPs with IBPs URIs
			for (int i = 0; i < this.biddersOBP.size(); i++) {
				this.doPortConnection(this.biddersOBP.get(i).getPortURI(), this.biddersIBP.get(i),
						EnglishBidderConnector.class.getCanonicalName());
			}
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public synchronized void execute() throws Exception {
		super.execute();

		for (BiddedObject biddedObject : englishSellerOutboundPort.getListOfGoods()) {
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
		this.doPortDisconnection(this.englishSellerOutboundPort.getPortURI());
		for (int i = 0; i < this.biddersOBP.size(); i++) {
			this.doPortDisconnection(this.biddersOBP.get(i).getPortURI());
		}

		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.englishSellerOutboundPort.unpublishPort();
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
	 * @param object the object bid for the auction
	 * @return boolean value
	 * @throws Exception when the number of thread is not sufficient
	 */
	public boolean acceptSellingRequest(BiddedObject object) throws Exception {
		if ((this.numberOfThreads >= this.englishSellerOutboundPort.getListOfGoods().size()))
			return true;
		else {
			throw new Exception("Number of thread not sufficient");
		}
	}

	/**
	 * Method verifying and add bidders to the list of participation request for an
	 * English bidding
	 * 
	 * @param bidder the bidder
	 * @return boolean value
	 */
	public boolean acceptParticipationRequest(EnglishBidder bidder) {
		if (this.bidders.size() <= this.maxAutorizedBidder) {
			// EnglishAuctioneer.currentParticipants++;
			// adding the bidder so they can make offers
			this.bidders.add(bidder);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to add the offer in the hash map with the id of the bidder associated
	 * with the price offered
	 * 
	 * @param offers    the offers hash map
	 * @param offerMade the offer made by a bidder
	 */
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offerMade) {
		offers.put(offerMade.getBidderId(), offerMade.getPriceOffer());
	}

	/**
	 * Method to return the Entry of the maximal offer received
	 * 
	 * @param offers hash map of biddersId and their price
	 * @return Max offer and the bidder
	 */
	public Entry<String, Float> getMaxOffers(ConcurrentHashMap<String, Float> offers) {
		Map.Entry<String, Float> maxentry = null;
		for (Map.Entry<String, Float> entry : offers.entrySet()) {
			if (maxentry == null || entry.getValue().compareTo(maxentry.getValue()) > 0) {
				maxentry = entry;
			}
		}
		return maxentry;
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
	 * Method getting the current price for an item during an auction
	 * 
	 * @param state the protocol state
	 * @return the current price
	 */
	public float getCurrentPrice(ProtocolState state) {
		if (state.getOffers().size() == 0 || state.getOffers() == null) {
			return state.getBiddedObject().getPriceReference();
		} else {
			setCurrentPrice(this.getMaxOffers(state.getOffers()).getValue());
			return this.currentPriceReference;
		}
	}

	/**
	 * Method returning the list of potential bidders for the auction of an item
	 * 
	 * @return array list of potential bidders
	 */
	private ArrayList<EnglishBidderOutboundPort> getPotentialBidders() {
		return this.biddersOBP;
	}

	/**
	 * Method returning the list of bid object
	 * 
	 * @return list of bid object
	 */
	public Vector<BiddedObject> getBiddedObjectList() {
		return this.biddedObjectList;
	}

	/**
	 * Method getting the list of bidders participating in the English bidding
	 * 
	 * @return array list of bidders
	 */
	public Vector<EnglishBidder> getBidders() {
		return this.bidders;
	}

	/**
	 * Method getting the current number of participants during an auction
	 * 
	 * @return number of participants
	 */
	public int getCurrentParticipants() {
		return this.currentParticipants;
	}

	/**
	 * Method setting the new current price during an auction
	 * 
	 * @param currentPrice the new current price
	 */
	public void setCurrentPrice(float currentPrice) {
		this.currentPriceReference = currentPrice;
	}

	/**
	 * Method setting the number of participants with the value of
	 * currentParticipants
	 * 
	 * @param currentParticipants number of current participants
	 */
	public void setCurrentParticipants(int currentParticipants) {
		this.currentParticipants = currentParticipants;
	}

	/****************************************************************************
	 * * * * * ENGLISH BIDDING PROTOCOL IMPLEMENTATION * * * * *
	 ****************************************************************************/

	/**
	 * Method initializing the English auction
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling an selling request from an outbound port
	 */
	public void initializeBid(ProtocolState state) throws Exception {
		if (state.getState() == ProtocolProgress.Init) {
			boolean responseToSellingRequest = false;
			// on suppose qu'on met en enchère le premier objet de la liste
			// requete acceptée
			// rajouter le bien à la liste des enchères
			responseToSellingRequest = this.englishSellerOutboundPort.sellingRequest(state.getBiddedObject());
			if (responseToSellingRequest) {
				state.setState(ProtocolProgress.PermissionToParticipate);
			}
		}
	}

	/**
	 * Method adding participants to the auction
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from an outbound port
	 */
	public void addParticipants(ProtocolState state) throws Exception {
		if (state.getState() == ProtocolProgress.PermissionToParticipate) {
			for (int i = 0; i < this.getPotentialBidders().size(); i++) {
				try {
					EnglishBidderOutboundPort bidderOBP = this.getPotentialBidders().get(i);
					if (state.getBiddedObject().getObjectId() == bidderOBP.getObjectInterestedIn().getObjectId())
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
	 * Launch the English protocol
	 * 
	 * @param state the protocol state at the launch
	 * @throws Exception when calling one of the method that already throws an
	 *                   exception (initializeBid, addParticipant,auction or
	 *                   closeBid)
	 */
	private void launchProtocol(ProtocolState state) throws Exception {
		this.initializeBid(state);
		System.out.println("The auction id {" + state.getBiddedObject().getObjectId() + "}, "
				+ state.getBiddedObject().getDescription() + " start at " + state.getBiddedObject().getPriceReference()
				+ "€\n\t---");
		this.addParticipants(state);

		this.auction(state);

		this.closeBid(state);
	}

	/**
	 * Method with the bidder action when he want to make a bid
	 * 
	 * @param state  the protocol state
	 * @param bidder the bidder
	 * @throws Exception when calling a method of bidder of thread.sleep()
	 */
	private void bidderAction(ProtocolState state, EnglishBidder bidder) throws Exception {
		float priceOffered = bidder.generatePertinentOffer(state);
		if (priceOffered > 0) {
			Offer offer = bidder.makeAnOffer(state.getBiddedObject(), priceOffered);
			this.addOffer(state.getOffers(), offer);
			bidInformations(state, bidder, offer);
			Thread.sleep(1000);
		}
	}

	/**
	 * Method containing informations about the bid
	 * 
	 * @param state  the protocol state
	 * @param bidder the bidder
	 * @param offer  the offer made by the bidder
	 */
	private void bidInformations(ProtocolState state, EnglishBidder bidder, Offer offer) {
		System.out.println(bidder.getBidderId() + " bid for {" + state.getBiddedObject().getObjectId() + "} with "
				+ offer.getPriceOffer() + "€");
		System.out.println("Is there another offer above " + offer.getPriceOffer() + "€ for {"
				+ state.getBiddedObject().getObjectId() + "} ?");
	}

	/**
	 * Method conducting the English bidding
	 * 
	 * @param state the protocol state
	 * @throws Exception using the semaphore, sleep, or calling a method from an
	 *                   outbound port
	 */
	public void auction(ProtocolState state) throws Exception {
		// this loop should stop when we got only one participant
		if (state.getState() == ProtocolProgress.Bidding) {

			while (this.getBidders().size() != 1) {

				for (int i = 0; i < this.getBidders().size(); i++) {
					this.lock.release();
					this.lock.acquire();
					EnglishBidder bidder = this.getBidders().get(i);

					boolean wantTobid = bidder.wantToMakeAnOffer(state);

					boolean bidderInterestedInAuction = state.getBiddedObject().getObjectId() == bidder
							.getObjectInterestedIn().getObjectId();

					if (wantTobid && bidderInterestedInAuction) {
						bidderAction(state, bidder);
					}
					this.lock.release();
				}
				Thread.sleep(1000);
			}
			state.setState(ProtocolProgress.OpenEnvelopes);
		}
	}

	/**
	 * Method closing the bid
	 * 
	 * @param state the protocol state
	 * @throws Exception using the semaphore, sleep, or calling a method from an
	 *                   outbound port
	 */
	public void closeBid(ProtocolState state) throws Exception {
		this.lock.acquire();

		if (state.getState() == ProtocolProgress.OpenEnvelopes) {
			System.out.println(
					"Ranking {" + state.getBiddedObject().getObjectId() + "}: " + state.sortOffers(state.getOffers()));
			this.announceWinner(state);
			this.notifySeller(state);
			System.out.println("The english auction {" + state.getBiddedObject().getObjectId()
					+ "} is closed \nThanks for participating.");
			state.setState(ProtocolProgress.Closed);
		}
	}

	/**
	 * Method announcing the winner of the English bidding with the id and the offer
	 * made
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from an outbound port
	 */
	public void announceWinner(ProtocolState state) throws Exception {
		assert state.getOffers().size() > 0;
		Entry<String, Float> winnerReferences = this.getMaxOffers(state.getOffers());
		System.out.println("The winner of the auction {" + state.getBiddedObject().getObjectId() + "} is "
				+ winnerReferences.getKey() + " and made an offer of " + winnerReferences.getValue() + "€");

		state.setState(ProtocolProgress.NotifySeller);
	}

	/**
	 * Method notifying the seller that his item has been sold
	 * 
	 * @param state the protocol state
	 */
	public void notifySeller(ProtocolState state) {
		if (state.getState() == ProtocolProgress.NotifySeller) {
			state.getBiddedObject().setSelled(true);
			System.out.println("The seller of the english bidding has been notified.");
			state.setState(ProtocolProgress.PreparingClosure);
		}
	}

}
