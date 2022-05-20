package plugins.englishAuction.ports;

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
 * English Auctioneer inbound port for plugin
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishAuctioneerInboundPortForPlugin extends AbstractInboundPort implements EnglishAuctioneerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the inbound port instance with the URI
	 * 
	 * @param uri       the unique identifier of the port
	 * @param owner     the owner of the component owning the port
	 * @param pluginURI the unique identifier of the port with plugin
	 * @throws Exception
	 * 
	 */
	public EnglishAuctioneerInboundPortForPlugin(String uri, ComponentI owner, String pluginURI) throws Exception {
		super(uri, EnglishAuctioneerCI.class, owner, pluginURI, null);
		assert uri != null && !uri.isEmpty();
	}

	/**
	 * Creating the inbound port instance
	 * 
	 * @param owner     the owner of the component owning the port
	 * @param pluginURI the unique identifier of the port with plugin
	 * @throws Exception
	 * 
	 */
	public EnglishAuctioneerInboundPortForPlugin(ComponentI owner, String pluginURI) throws Exception {
		super(EnglishAuctioneerCI.class, owner, pluginURI, null);
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
