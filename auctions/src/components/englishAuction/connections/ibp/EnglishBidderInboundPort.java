package components.englishAuction.connections.ibp;

import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishBidderCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * English bidder inbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishBidderInboundPort extends AbstractInboundPort implements EnglishBidderCI {

	private static final long serialVersionUID = 1L;

	public EnglishBidderInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, EnglishBidderCI.class, owner);
		assert uri != null && !uri.isEmpty();
	}

	public EnglishBidderInboundPort(ComponentI owner) throws Exception {
		super(EnglishBidderCI.class, owner);
	}

	@Override
	public boolean participationRequest() throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject objectB, float priceOffered) throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).makeAnOffer(objectB, priceOffered);
	}

	@Override
	public void retrieve() throws Exception {
		this.getOwner().handleRequest(b -> (EnglishBidder) b).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return this.getBidderId();
	}

	@Override
	public boolean wantToMakeAnOffer(ProtocolState state) throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).wantToMakeAnOffer(state);
	}

	@Override
	public float generatePertinentOffer(ProtocolState state) throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).generatePertinentOffer(state);
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).getObjectInterestedIn();
	}

}
