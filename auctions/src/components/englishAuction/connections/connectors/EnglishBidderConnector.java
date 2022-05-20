package components.englishAuction.connections.connectors;

import components.englishAuction.interfaces.EnglishBidderCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * English bidder connector
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishBidderConnector extends AbstractConnector implements EnglishBidderCI {

	@Override
	public boolean participationRequest() throws Exception {
		return ((EnglishBidderCI) this.offering).participationRequest();
	}

	@Override
	public Offer makeAnOffer(BiddedObject objectB, float priceOffered) throws Exception {
		return ((EnglishBidderCI) this.offering).makeAnOffer(objectB, priceOffered);
	}

	@Override
	public void retrieve() throws Exception {
		((EnglishBidderCI) this.offering).retrieve();
	}

	@Override
	public boolean isInRange(float price) throws Exception {
		return ((EnglishBidderCI) this.offering).isInRange(price);
	}

	@Override
	public String getBidderId() throws Exception {
		return ((EnglishBidderCI) this.offering).getBidderId();
	}

	@Override
	public boolean wantToMakeAnOffer(ProtocolState state) throws Exception {
		return ((EnglishBidderCI) this.offering).wantToMakeAnOffer(state);
	}

	@Override
	public float generatePertinentOffer(ProtocolState state) throws Exception {
		return ((EnglishBidderCI) this.offering).generatePertinentOffer(state);
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return ((EnglishBidderCI) this.offering).getObjectInterestedIn();
	}

}
