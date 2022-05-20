package components.englishAuction.connections.obp;

import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishAuctioneerCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * English auctioneer outbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishAuctioneerOutboundPort extends AbstractOutboundPort implements EnglishAuctioneerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the outbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishAuctioneerOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, EnglishAuctioneerCI.class, owner);
	}

	/**
	 * Creating the outbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishAuctioneerOutboundPort(ComponentI owner) throws Exception {
		super(EnglishAuctioneerCI.class, owner);
	}

	@Override
	public void initializeBid(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).initializeBid(state);
	}

	@Override
	public void addParticipants(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).addParticipants(state);
	}

	@Override
	public void auction(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).auction(state);
	}

	@Override
	public void closeBid(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).closeBid(state);
	}

	@Override
	public void announceWinner(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).announceWinner(state);
	}

	@Override
	public void notifySeller(ProtocolState state) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).notifySeller(state);
	}

	@Override
	public boolean acceptSellingRequest(BiddedObject objectBidded) throws Exception {
		return ((EnglishAuctioneerCI) this.getConnector()).acceptSellingRequest(objectBidded);
	}

	@Override
	public boolean acceptParticipationRequest(EnglishBidder bidder) throws Exception {
		return ((EnglishAuctioneerCI) this.getConnector()).acceptParticipationRequest(bidder);
	}

	@Override
	public void addObjectToBid(BiddedObject objectBidded) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).addObjectToBid(objectBidded);
	}

	@Override
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).addOffer(offers, offer);
	}

	@Override
	public int getCurrentParticipants() throws Exception {
		return ((EnglishAuctioneerCI) this.getConnector()).getCurrentParticipants();
	}

	@Override
	public Vector<EnglishBidder> getBidders() throws Exception {
		return ((EnglishAuctioneerCI) this.getConnector()).getBidders();
	}

	@Override
	public Entry<String, Float> getMaxOffers(ConcurrentHashMap<String, Float> offers) throws Exception {
		return ((EnglishAuctioneerCI) this.getConnector()).getMaxOffers(offers);
	}

	@Override
	public float getCurrentPrice(ProtocolState state) throws Exception {
		return ((EnglishAuctioneerCI) this.getConnector()).getCurrentPrice(state);
	}

	@Override
	public void setCurrentPrice(float currentPrice) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).setCurrentPrice(currentPrice);
	}

	@Override
	public void setCurrentParticipants(int c) throws Exception {
		((EnglishAuctioneerCI) this.getConnector()).setCurrentParticipants(c);
	}

}
