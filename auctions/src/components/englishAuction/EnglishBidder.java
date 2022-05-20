package components.englishAuction;

import com.github.javafaker.Faker;

import components.englishAuction.connections.connectors.EnglishAuctioneerConnector;
import components.englishAuction.connections.ibp.EnglishBidderInboundPort;
import components.englishAuction.connections.obp.EnglishAuctioneerOutboundPort;
import components.englishAuction.interfaces.EnglishAuctioneerCI;
import entities.Behavior;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import plugins.englishAuction.EnglishAuctioneerPlugin;
import plugins.englishAuction.EnglishBidderPlugin;

/**
 * Class that represent an English bidder component, this class is supposed to
 * allow bidder to make offers
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
@RequiredInterfaces(required = { EnglishAuctioneerCI.class })
public class EnglishBidder extends AbstractComponent {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private String bidderId;
	/**
	 * uri for the English bidder
	 */
	public static String englishBidderIBP_URI;
	protected EnglishBidderInboundPort englishBidderInboundPort;
	protected EnglishAuctioneerOutboundPort englishAuctioneerOutboundPort;

	private float rangeMin;
	private float rangeMax;
	@SuppressWarnings("unused")
	private boolean isParticipating;
	private Behavior type;
	private BiddedObject interestedIn;

	// Plugin
	protected EnglishBidderPlugin englishBidderPlugin;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	protected EnglishBidder(float rangeMin, float rangeMax, BiddedObject interestedIn, String englishBidderIBP_URI,
			Behavior type) throws Exception {
		super(1, 0);

		EnglishBidder.englishBidderIBP_URI = englishBidderIBP_URI;
		this.interestedIn = interestedIn;

		// Plugin bidder
		this.englishBidderPlugin = new EnglishBidderPlugin();
		this.englishBidderPlugin.setPluginURI(EnglishBidder.englishBidderIBP_URI);
		this.installPlugin(this.englishBidderPlugin);

		this.englishAuctioneerOutboundPort = new EnglishAuctioneerOutboundPort(this);
		this.englishAuctioneerOutboundPort.publishPort();

		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;

		this.isParticipating = false;

		Faker faker = new Faker();
		this.bidderId = faker.name().firstName();
		this.type = type;
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();
		try {
			this.doPortConnection(this.englishAuctioneerOutboundPort.getPortURI(),
					EnglishAuctioneerPlugin.englishAuctioneerPlugin_URI,
					EnglishAuctioneerConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public synchronized void execute() throws Exception {
		super.execute();
	}

	@Override
	public synchronized void finalise() throws Exception {
		this.doPortDisconnection(this.englishAuctioneerOutboundPort.getPortURI());
		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.englishAuctioneerOutboundPort.unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method that send a participation request to the auction
	 * 
	 * @return a response, true if the participation is accepted false else
	 * @throws Exception when calling a method from an outbound Port
	 */
	public boolean participationRequest() throws Exception {
		if (this.englishAuctioneerOutboundPort.acceptParticipationRequest(this)) {
			this.englishAuctioneerOutboundPort
					.setCurrentParticipants(this.englishAuctioneerOutboundPort.getCurrentParticipants() + 1);
			this.isParticipating = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method that return an offer
	 * 
	 * @param object     the concerned object
	 * @param priceOffer the price
	 * @return an offer
	 */
	public Offer makeAnOffer(BiddedObject object, float priceOffer) {
		Offer offer = new Offer(this.getBidderId(), object, priceOffer);
		this.rangeMin = (int) priceOffer;
		return offer;

	}

	/**
	 * This retrieve method update the number of the participant
	 * 
	 * @throws Exception when calling a method from an outbound Port
	 */
	public void retrieve() throws Exception {
		if (this.englishAuctioneerOutboundPort.getBidders().size() > 1) {
			this.isParticipating = false;
			this.englishAuctioneerOutboundPort.getBidders().remove(this);
			System.out.println(this.getBidderId() + " is retrieving from the auction.");
		}
		this.englishAuctioneerOutboundPort
				.setCurrentParticipants(this.englishAuctioneerOutboundPort.getCurrentParticipants() - 1);
	}

	/**
	 * Method that test if a bidder can afford a object with it price
	 * 
	 * @param price the price of the concerned object
	 * @return boolean return if the price is included between rangeMin and Max
	 */
	public boolean isInRange(float price) {
		return price >= this.rangeMin && price <= this.rangeMax;
	}

	/**
	 * Method that represent the willing of the bidder in making an offer
	 * 
	 * @param state the protocol state
	 * @return boolean true if the bidder wants to submit an offer, else false
	 * @throws Exception when calling a method from an outboundPort
	 */
	public boolean wantToMakeAnOffer(ProtocolState state) throws Exception {
		try {
			if (this.rangeMax < this.englishAuctioneerOutboundPort.getCurrentPrice(state)) {
				this.retrieve();
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method to generate a pertinent offer according to the type of the bidder
	 * 
	 * @param state the protocol state
	 * @return the proposed price
	 * @throws Exception when calling a method from an outbound Port
	 */
	public float generatePertinentOffer(ProtocolState state) throws Exception {
		float min = this.englishAuctioneerOutboundPort.getCurrentPrice(state);
		float value = min + type.percent * min / 100;
		if (this.rangeMax < min || value > this.rangeMax) {
			this.retrieve();
			return 0;
		} else {
			return value;
		}
	}

	/**
	 * Method to get the bid object interested by a bidder
	 * 
	 * @return bid object
	 */
	public BiddedObject getObjectInterestedIn() {
		return this.interestedIn;
	}

	/**
	 * Method to get the bidder's name
	 * 
	 * @return BidderId
	 */
	public String getBidderId() {
		return bidderId;
	}

}
