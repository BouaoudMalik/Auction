package plugins.threshold;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.threshold.ThresholdAuctioneer;
import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import plugins.threshold.ports.ThresholdAuctioneerInboundPortForPlugin;

/**
 * Threshold Auctioneer plugin class
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdAuctioneerPlugin extends AbstractPlugin implements ThresholdAuctioneerCI {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected ThresholdAuctioneerInboundPortForPlugin thresholdAuctioneerIBP;
	public String thresholdAuctioneerPlugin_URI;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public ThresholdAuctioneerPlugin(String thresholdAuctioneerPlugin_URI) {
		super();
		this.thresholdAuctioneerPlugin_URI = thresholdAuctioneerPlugin_URI;
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

		this.addOfferedInterface(ThresholdAuctioneerCI.class);
		this.thresholdAuctioneerIBP = new ThresholdAuctioneerInboundPortForPlugin(this.thresholdAuctioneerPlugin_URI,
				this.getOwner(), this.getPluginURI());
		this.thresholdAuctioneerIBP.publishPort();
	}

	@Override
	public void uninstall() throws Exception {
		this.thresholdAuctioneerIBP.unpublishPort();
		this.thresholdAuctioneerIBP.destroyPort();
		this.removeOfferedInterface(ThresholdAuctioneerCI.class);
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	@Override
	public void initializeBid(ProtocolState state) throws Exception {
		this.getOwner().runTask(a -> {
			try {
				initializeBid(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void addParticipants(ProtocolState state) throws Exception {
		this.getOwner().runTask(a -> {
			try {
				addParticipants(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void auction(ProtocolState state) throws Exception {
		this.getOwner().runTask(a -> {
			try {
				auction(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void closeBid(ProtocolState state) throws Exception {
		this.getOwner().runTask(a -> {
			try {
				closeBid(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void announceWinner(ProtocolState state) throws Exception {
		this.getOwner().runTask(a -> {
			try {
				announceWinner(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void notifySeller(ProtocolState state) throws Exception {
		this.getOwner().runTask(a -> {
			try {
				notifySeller(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public boolean acceptSellingRequest(BiddedObject object) throws Exception {
		return this.getOwner().handleRequest(a -> (ThresholdAuctioneer) a).acceptSellingRequest(object);
	}

	@Override
	public boolean acceptParticipationRequest(ThresholdBidder bidder) throws Exception {
		return this.getOwner().handleRequest(a -> (ThresholdAuctioneer) a).acceptParticipationRequest(bidder);
	}

	@Override
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception {
		this.getOwner().handleRequest(a -> (ThresholdAuctioneer) a).addOffer(offers, offer);
	}

	@Override
	public Vector<ThresholdBidder> getBidders() throws Exception {
		return this.getOwner().handleRequest(a -> (ThresholdAuctioneer) a).getBidders();
	}

}
