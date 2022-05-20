package plugins.englishAuction;

import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import plugins.englishAuction.ports.EnglishBidderInboundPortForPlugin;
import components.englishAuction.EnglishBidder;
import components.englishAuction.interfaces.EnglishBidderCI;

/**
 * English Bidder plugin class
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishBidderPlugin extends AbstractPlugin implements EnglishBidderCI {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected EnglishBidderInboundPortForPlugin englishBidderIBP;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public EnglishBidderPlugin() throws Exception {
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

		this.addOfferedInterface(EnglishBidderCI.class);
		this.englishBidderIBP = new EnglishBidderInboundPortForPlugin(EnglishBidder.englishBidderIBP_URI,
				this.getOwner(), this.getPluginURI());
		this.englishBidderIBP.publishPort();
	}

	@Override
	public void uninstall() throws Exception {
		this.englishBidderIBP.unpublishPort();
		this.englishBidderIBP.destroyPort();
		this.removeOfferedInterface(EnglishBidderCI.class);
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	@Override
	public boolean participationRequest() throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).participationRequest();
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
	public Offer makeAnOffer(BiddedObject object, float priceOffer) throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).makeAnOffer(object, priceOffer);
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
		return this.getOwner().handleRequest(b -> (EnglishBidderCI) b).getBidderId();
	}

	@Override
	public BiddedObject getObjectInterestedIn() throws Exception {
		return this.getOwner().handleRequest(b -> (EnglishBidder) b).getObjectInterestedIn();
	}

}
