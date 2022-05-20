package components.threshold.connections.connectors;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * Threshold auctioneer connector
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdAuctioneerConnector extends AbstractConnector implements ThresholdAuctioneerCI {

	@Override
	public void initializeBid(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.offering).initializeBid(state);
	}

	@Override
	public void addParticipants(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.offering).addParticipants(state);
	}

	@Override
	public void auction(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.offering).auction(state);
	}

	@Override
	public void closeBid(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.offering).closeBid(state);
	}

	@Override
	public void announceWinner(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.offering).announceWinner(state);
	}

	@Override
	public void notifySeller(ProtocolState state) throws Exception {
		((ThresholdAuctioneerCI) this.offering).notifySeller(state);
	}

	@Override
	public boolean acceptSellingRequest(BiddedObject objectBidded) throws Exception {
		return ((ThresholdAuctioneerCI) this.offering).acceptSellingRequest(objectBidded);
	}

	@Override
	public boolean acceptParticipationRequest(ThresholdBidder bidder) throws Exception {
		return ((ThresholdAuctioneerCI) this.offering).acceptParticipationRequest(bidder);
	}

	@Override
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception {
		((ThresholdAuctioneerCI) this.offering).addOffer(offers, offer);
	}

	@Override
	public Vector<ThresholdBidder> getBidders() throws Exception {
		return ((ThresholdAuctioneerCI) this.offering).getBidders();
	}

}
