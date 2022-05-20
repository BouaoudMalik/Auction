package plugins.threshold.ports;

import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdBidderCI;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * Threshold Bidder inbound port for plugin
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdBidderInboundPortForPlugin extends AbstractInboundPort implements ThresholdBidderCI {

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
	public ThresholdBidderInboundPortForPlugin(String uri, ComponentI owner, String pluginURI) throws Exception {
		super(uri, ThresholdBidderCI.class, owner, pluginURI, null);
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
	public ThresholdBidderInboundPortForPlugin(ComponentI owner, String pluginURI) throws Exception {
		super(ThresholdBidderCI.class, owner, pluginURI, null);
	}

	@Override
	public boolean participationRequest() throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject object) throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).makeAnOffer(object);
	}

	@Override
	public void retrieve() throws Exception {
		this.getOwner().handleRequest(b -> (ThresholdBidder) b).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return this.getBidderId();
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).getObjectInterestedIn();
	}

}
