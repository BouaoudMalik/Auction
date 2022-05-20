package components.threshold.connections.obp;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * Threshold auctioneer outbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdAuctioneerOutboundPort extends AbstractOutboundPort implements ThresholdAuctioneerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the outbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdAuctioneerOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ThresholdAuctioneerCI.class, owner);
	}

	/**
	 * Creating the outbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdAuctioneerOutboundPort(ComponentI owner) throws Exception {
		super(ThresholdAuctioneerCI.class, owner);
	}

	@Override
	public void initializeBid(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).initializeBid(state);
	}

	@Override
	public void addParticipants(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).addParticipants(state);
	}

	@Override
	public void auction(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).auction(state);
	}

	@Override
	public void closeBid(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).closeBid(state);
	}

	@Override
	public void announceWinner(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).announceWinner(state);
	}

	@Override
	public void notifySeller(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).notifySeller(state);
	}

	@Override
	public boolean acceptSellingRequest(BiddedObject objectBidded) throws Exception {
		return ((ThresholdAuctioneerCI) this.getConnector()).acceptSellingRequest(objectBidded);
	}

	@Override
	public boolean acceptParticipationRequest(ThresholdBidder bidder) throws Exception {
		return ((ThresholdAuctioneerCI) this.getConnector()).acceptParticipationRequest(bidder);
	}

	@Override
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception {
		((ThresholdAuctioneerCI) this.getConnector()).addOffer(offers, offer);
	}

	@Override
	public Vector<ThresholdBidder> getBidders() throws Exception {
		return ((ThresholdAuctioneerCI) this.getConnector()).getBidders();
	}

}
