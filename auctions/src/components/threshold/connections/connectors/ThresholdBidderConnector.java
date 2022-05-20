package components.threshold.connections.connectors;

import components.threshold.interfaces.ThresholdBidderCI;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * Threshold bidder connector
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdBidderConnector extends AbstractConnector implements ThresholdBidderCI {

	@Override
	public boolean participationRequest() throws Exception {
		return ((ThresholdBidderCI) this.offering).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject objectB) throws Exception {
		return ((ThresholdBidderCI) this.offering).makeAnOffer(objectB);
	}

	@Override
	public void retrieve() throws Exception {
		((ThresholdBidderCI) this.offering).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return ((ThresholdBidderCI) this.offering).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return ((ThresholdBidderCI) this.offering).getBidderId();
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return ((ThresholdBidderCI) this.offering).getObjectInterestedIn();
	}

}
