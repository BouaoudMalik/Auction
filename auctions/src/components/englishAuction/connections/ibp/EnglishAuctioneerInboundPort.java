package components.englishAuction.connections.ibp;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Vector;

import components.englishAuction.EnglishAuctioneer;
import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * English auctioneer inbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishAuctioneerInboundPort extends AbstractInboundPort implements EnglishAuctioneerCI {

	private static final long serialVersionUID = 1L;

	public EnglishAuctioneerInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, EnglishAuctioneerCI.class, owner);
		assert uri != null && !uri.isEmpty();
	}

	public EnglishAuctioneerInboundPort(ComponentI owner) throws Exception {
		super(EnglishAuctioneerCI.class, owner);
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
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).acceptSellingRequest(objectBidded);
	}

	@Override
	public boolean acceptParticipationRequest(EnglishBidder bidder) throws Exception {
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).acceptParticipationRequest(bidder);
	}

	@Override
	public void addObjectToBid(BiddedObject objectBidded) throws Exception {
		this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).addObjectToBid(objectBidded);
	}

	@Override
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception {
		this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).addOffer(offers, offer);
	}

	@Override
	public int getCurrentParticipants() throws Exception {
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).getCurrentParticipants();
	}

	@Override
	public Vector<EnglishBidder> getBidders() throws Exception {
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).getBidders();
	}

	@Override
	public Entry<String, Float> getMaxOffers(ConcurrentHashMap<String, Float> offers) throws Exception {
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).getMaxOffers(offers);
	}

	@Override
	public float getCurrentPrice(ProtocolState state) throws Exception {
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).getCurrentPrice(state);
	}

	@Override
	public void setCurrentPrice(float currentPrice) throws Exception {
		this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).setCurrentPrice(currentPrice);
	}

	@Override
	public void setCurrentParticipants(int c) throws Exception {
		this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).setCurrentParticipants(c);
	}

}
