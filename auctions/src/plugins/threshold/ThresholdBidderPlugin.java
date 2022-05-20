package plugins.threshold;

import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdBidderCI;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import plugins.threshold.ports.ThresholdBidderInboundPortForPlugin;

/**
 * Threshold Bidder plugin class
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdBidderPlugin extends AbstractPlugin implements ThresholdBidderCI {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected ThresholdBidderInboundPortForPlugin thresholdBidderIBP;
	private String thresholdBidderIBP_URI;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public ThresholdBidderPlugin(String thresholdBidderIBP_URI) throws Exception {
		super();
		this.thresholdBidderIBP_URI = thresholdBidderIBP_URI;
	}

	// -------------------------------------------------------------------------
	// Plugin life-cycle
	// -------------------------------------------------------------------------

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
	}

	@Override
	public void initialise() throws Exception {
		super.initialise();

		this.addOfferedInterface(ThresholdBidderCI.class);
		this.thresholdBidderIBP = new ThresholdBidderInboundPortForPlugin(this.thresholdBidderIBP_URI, this.getOwner(),
				this.getPluginURI());
		this.thresholdBidderIBP.publishPort();
	}

	@Override
	public void uninstall() throws Exception {
		this.thresholdBidderIBP.unpublishPort();
		this.thresholdBidderIBP.destroyPort();
		this.removeOfferedInterface(ThresholdBidderCI.class);
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	@Override
	public boolean participationRequest() throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject object) throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).makeAnOffer(object);
	}

	@Override
	public void retrieve() throws Exception {
		this.getOwner().handleRequest(b -> (ThresholdBidder) b).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).getBidderId();
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).getObjectInterestedIn();
	}

}
