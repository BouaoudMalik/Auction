package components.threshold.connections.obp;

import components.threshold.interfaces.ThresholdBidderCI;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * Threshold bidder outbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdBidderOutboundPort extends AbstractOutboundPort implements ThresholdBidderCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the outbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdBidderOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ThresholdBidderCI.class, owner);
	}

	/**
	 * Creating the outbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdBidderOutboundPort(ComponentI owner) throws Exception {
		super(ThresholdBidderCI.class, owner);
	}

	@Override
	public boolean participationRequest() throws Exception {
		return ((ThresholdBidderCI) this.getConnector()).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject objectB) throws Exception {
		return ((ThresholdBidderCI) this.getConnector()).makeAnOffer(objectB);
	}

	@Override
	public void retrieve() throws Exception {
		((ThresholdBidderCI) this.getConnector()).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return ((ThresholdBidderCI) this.getConnector()).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return ((ThresholdBidderCI) this.getConnector()).getBidderId();
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return ((ThresholdBidderCI) this.getConnector()).getObjectInterestedIn();
	}

}
