package components.threshold;

import com.github.javafaker.Faker;

import components.threshold.connections.connectors.ThresholdAuctioneerConnector;
import components.threshold.connections.ibp.ThresholdBidderInboundPort;
import components.threshold.connections.obp.ThresholdAuctioneerOutboundPort;
import components.threshold.interfaces.ThresholdAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import plugins.threshold.ThresholdBidderPlugin;

/**
 * Threshold auction Bidder components interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
@RequiredInterfaces(required = { ThresholdAuctioneerCI.class })
public class ThresholdBidder extends AbstractComponent {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	public String bidderId;
	public String thresholdBidderIBP_URI;

	protected ThresholdBidderInboundPort thresholdBidderInboundPort;
	protected ThresholdAuctioneerOutboundPort auctioneerOutboundPort;
	protected String thresholdAuctioneerPlugin_URI;

	private float rangeMin;
	private float rangeMax;
	private boolean isParticipating;
	private BiddedObject interestedIn;
	private boolean hasMadeAnOffer;

	// Plugin
	protected ThresholdBidderPlugin thresholdBidderPlugin;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	protected ThresholdBidder(int rangeMin, int rangeMax, BiddedObject interestedIn, String thresholdBidderIBP_URI,
			String thresholdAuctioneerPlugin_URI) throws Exception {
		super(1, 0);

		Faker faker = new Faker();
		this.hasMadeAnOffer = false;
		this.bidderId = faker.name().firstName();
		this.thresholdBidderIBP_URI = thresholdBidderIBP_URI;
		this.thresholdAuctioneerPlugin_URI = thresholdAuctioneerPlugin_URI;

		// Plugin bidder
		this.thresholdBidderPlugin = new ThresholdBidderPlugin(thresholdBidderIBP_URI);
		this.thresholdBidderPlugin.setPluginURI(this.thresholdBidderIBP_URI);
		this.installPlugin(this.thresholdBidderPlugin);

		this.auctioneerOutboundPort = new ThresholdAuctioneerOutboundPort(this);
		this.auctioneerOutboundPort.publishPort();

		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
		this.isParticipating = false;
		this.interestedIn = interestedIn;
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();

		try {
			this.doPortConnection(this.auctioneerOutboundPort.getPortURI(), this.thresholdAuctioneerPlugin_URI,
					ThresholdAuctioneerConnector.class.getCanonicalName());
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
		this.doPortDisconnection(this.auctioneerOutboundPort.getPortURI());

		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.auctioneerOutboundPort.unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method checking the participation request for an auction
	 * 
	 * @return boolean value
	 * @throws Exception when calling a method from an outbound port
	 */
	public boolean participationRequest() throws Exception {
		if (this.auctioneerOutboundPort.acceptParticipationRequest(this)) {
			this.isParticipating = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to make a bid for an object
	 * 
	 * @param object the bid object
	 * @return an offer
	 * @throws Exception when the bidder is not participating
	 */
	public Offer makeAnOffer(BiddedObject object) throws Exception {
		if (this.isParticipating) {
			Offer offer = new Offer(this.bidderId, object, (this.rangeMax + this.rangeMin) / 2);
			this.rangeMin = (this.rangeMax + this.rangeMin) / 2;
			return offer;
		} else {
			throw new Exception("The bidder is not participating");
		}
	}

	/**
	 * Method indicating that the bidder is no longer participating to the auction
	 */
	public void retrieve() {
		System.out.println("I am retrieving from the auction");
	}

	/**
	 * Method verifying the price range is between the minimum and the maximum range
	 * 
	 * @param price the bid price
	 * @return boolean value
	 */
	public boolean isInRange(float price) {
		return price >= this.rangeMin && price <= this.rangeMax;
	}

	/**
	 * Method checking the response to the announce for the bid object
	 * 
	 * @param id the bid object
	 * @return boolean value
	 */
	public boolean respondAnnouncement(BiddedObject id) {
		return true;
	}

	/**
	 * Method getting the bidder id
	 * 
	 * @return bidder id
	 */
	public String getBidderId() {
		return this.bidderId;
	}

	/**
	 * Method getting the bid object interested by a bidder
	 * 
	 * @return bid object
	 */
	public BiddedObject getObjectInterestedIn() {
		return this.interestedIn;
	}

	/**
	 * Method getting the boolean value of hasMadeAnOffer
	 * 
	 * @return boolean value
	 */
	public boolean getHasMadeAnOffer() {
		return this.hasMadeAnOffer;
	}

	/**
	 * Method setting a new boolean value for hasMadeAnOffer
	 * 
	 * @param hasMadeAnOffer boolean value
	 */
	public void setHasMadeAnOffer(boolean hasMadeAnOffer) {
		this.hasMadeAnOffer = hasMadeAnOffer;
	}

}
