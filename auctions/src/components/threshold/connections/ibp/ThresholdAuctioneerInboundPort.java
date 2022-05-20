package components.threshold.connections.ibp;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.threshold.ThresholdAuctioneer;
import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * Threshold auctioneer inbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdAuctioneerInboundPort extends AbstractInboundPort implements ThresholdAuctioneerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the inbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdAuctioneerInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ThresholdAuctioneerCI.class, owner);
		assert uri != null && !uri.isEmpty();
	}

	/**
	 * Creating the inbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdAuctioneerInboundPort(ComponentI owner) throws Exception {
		super(ThresholdAuctioneerCI.class, owner);
		assert owner instanceof ThresholdAuctioneer;
	}

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
	public boolean acceptSellingRequest(BiddedObject objectBidded) throws Exception {
		return this.getOwner().handleRequest(a -> (ThresholdAuctioneer) a).acceptSellingRequest(objectBidded);
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
