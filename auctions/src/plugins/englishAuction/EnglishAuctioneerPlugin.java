package plugins.englishAuction;

import java.util.Map.Entry;
import java.util.Vector;

import components.englishAuction.EnglishAuctioneer;
import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishAuctioneerCI;

import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.ComponentI;
import plugins.englishAuction.ports.EnglishAuctioneerInboundPortForPlugin;

import java.util.concurrent.*;

/**
 * English Auctioneer plugin class
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishAuctioneerPlugin extends AbstractPlugin implements EnglishAuctioneerCI {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected EnglishAuctioneerInboundPortForPlugin englishAuctioneerIBP;
	public static String englishAuctioneerPlugin_URI = AbstractPort.generatePortURI();

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public EnglishAuctioneerPlugin() throws Exception {
		super();
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

		this.addOfferedInterface(EnglishAuctioneerCI.class);
		this.englishAuctioneerIBP = new EnglishAuctioneerInboundPortForPlugin(this.englishAuctioneerPlugin_URI,
				this.getOwner(), this.getPluginURI());
		this.englishAuctioneerIBP.publishPort();
	}

	@Override
	public void uninstall() throws Exception {
		this.englishAuctioneerIBP.unpublishPort();
		this.englishAuctioneerIBP.destroyPort();
		this.removeOfferedInterface(EnglishAuctioneerCI.class);
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
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).acceptSellingRequest(object);
	}

	@Override
	public boolean acceptParticipationRequest(EnglishBidder bidder) throws Exception {
		return this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).acceptParticipationRequest(bidder);
	}

	@Override
	public void addObjectToBid(BiddedObject object) throws Exception {
		this.getOwner().handleRequest(a -> (EnglishAuctioneer) a).addObjectToBid(object);
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
