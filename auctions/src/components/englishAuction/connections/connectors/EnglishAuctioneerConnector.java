package components.englishAuction.connections.connectors;

import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishAuctioneerCI;

import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * English auctioneer connector
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishAuctioneerConnector extends AbstractConnector implements EnglishAuctioneerCI {

	@Override
	public void initializeBid(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.offering).initializeBid(state);
	}

	@Override
	public void addParticipants(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.offering).addParticipants(state);
	}

	@Override
	public void auction(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.offering).auction(state);
	}

	@Override
	public void closeBid(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.offering).closeBid(state);
	}

	@Override
	public void announceWinner(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.offering).announceWinner(state);
	}

	@Override
	public void notifySeller(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.offering).notifySeller(state);
	}

	@Override
	public boolean acceptSellingRequest(BiddedObject objectBidded) throws Exception {
		return ((EnglishAuctioneerCI) this.offering).acceptSellingRequest(objectBidded);
	}

	@Override
	public boolean acceptParticipationRequest(EnglishBidder bidder) throws Exception {
		return ((EnglishAuctioneerCI) this.offering).acceptParticipationRequest(bidder);
	}

	@Override
	public void addObjectToBid(BiddedObject objectBidded) throws Exception {
		((EnglishAuctioneerCI) this.offering).addObjectToBid(objectBidded);
	}

	@Override
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception {
		((EnglishAuctioneerCI) this.offering).addOffer(offers, offer);
	}

	@Override
	public int getCurrentParticipants() throws Exception {
		return ((EnglishAuctioneerCI) this.offering).getCurrentParticipants();
	}

	@Override
	public Vector<EnglishBidder> getBidders() throws Exception {
		return ((EnglishAuctioneerCI) this.offering).getBidders();
	}

	@Override
	public Entry<String, Float> getMaxOffers(ConcurrentHashMap<String, Float> offers) throws Exception {
		return ((EnglishAuctioneerCI) this.offering).getMaxOffers(offers);
	}

	@Override
	public float getCurrentPrice(ProtocolState state) throws Exception {
		return ((EnglishAuctioneerCI) this.offering).getCurrentPrice(state);
	}

	@Override
	public void setCurrentPrice(float currentPrice) throws Exception {
		((EnglishAuctioneerCI) this.offering).setCurrentPrice(currentPrice);
	}

	@Override
	public void setCurrentParticipants(int c) throws Exception {
		((EnglishAuctioneerCI) this.offering).setCurrentParticipants(c);
	}

}
