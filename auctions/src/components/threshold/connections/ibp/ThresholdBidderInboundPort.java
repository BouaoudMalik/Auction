package components.threshold.connections.ibp;

import components.threshold.ThresholdBidder;
import components.threshold.interfaces.ThresholdBidderCI;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * Threshold bidder inbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdBidderInboundPort extends AbstractInboundPort implements ThresholdBidderCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the inbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdBidderInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ThresholdBidderCI.class, owner);
		assert uri != null && !uri.isEmpty();
	}

	/**
	 * Creating the inbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdBidderInboundPort(ComponentI owner) throws Exception {
		super(ThresholdBidderCI.class, owner);
	}

	@Override
	public boolean participationRequest() throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject objectB) throws Exception {
		return this.getOwner().handleRequest(b -> (ThresholdBidder) b).makeAnOffer(objectB);
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
