package plugins.englishAuction.ports;

import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishBidderCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * English Bidder inbound port for plugin
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishBidderInboundPortForPlugin extends AbstractInboundPort implements EnglishBidderCI {

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
	public EnglishBidderInboundPortForPlugin(String uri, ComponentI owner, String pluginURI) throws Exception {
		super(uri, EnglishBidderCI.class, owner, pluginURI, null);
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
	public EnglishBidderInboundPortForPlugin(ComponentI owner, String pluginURI) throws Exception {
		super(EnglishBidderCI.class, owner, pluginURI, null);
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
