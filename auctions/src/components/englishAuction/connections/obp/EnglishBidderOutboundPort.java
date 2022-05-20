package components.englishAuction.connections.obp;

import components.englishAuction.interfaces.EnglishBidderCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * English bidder outbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishBidderOutboundPort extends AbstractOutboundPort implements EnglishBidderCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the outbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishBidderOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, EnglishBidderCI.class, owner);
	}

	/**
	 * Creating the outbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishBidderOutboundPort(ComponentI owner) throws Exception {
		super(EnglishBidderCI.class, owner);
	}

	@Override
	public boolean participationRequest() throws Exception {
		return ((EnglishBidderCI) this.getConnector()).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject objectB, float priceOffered) throws Exception {
		return ((EnglishBidderCI) this.getConnector()).makeAnOffer(objectB, priceOffered);
	}

	@Override
	public void retrieve() throws Exception {
		((EnglishBidderCI) this.getConnector()).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return ((EnglishBidderCI) this.getConnector()).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return ((EnglishBidderCI) this.getConnector()).getBidderId();
	}

	@Override
	public boolean wantToMakeAnOffer(ProtocolState state) throws Exception {
		return ((EnglishBidderCI) this.getConnector()).wantToMakeAnOffer(state);
	}

	@Override
	public float generatePertinentOffer(ProtocolState state) throws Exception {
		return ((EnglishBidderCI) this.getConnector()).generatePertinentOffer(state);
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return ((EnglishBidderCI) this.getConnector()).getObjectInterestedIn();
	}

}
