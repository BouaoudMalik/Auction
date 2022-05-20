package plugins.englishAuction;

import java.util.ArrayList;

import components.englishAuction.EnglishSeller;
import components.englishAuction.interfaces.EnglishSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.ComponentI;
import plugins.englishAuction.ports.EnglishSellerInboundPortForPlugin;

/**
 * English Seller plugin class
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishSellerPlugin extends AbstractPlugin implements EnglishSellerCI {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected EnglishSellerInboundPortForPlugin englishSellerIBP;
	public static String englishSellerPlugin_URI = AbstractPort.generatePortURI();

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public EnglishSellerPlugin() throws Exception {
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
		this.addOfferedInterface(EnglishSellerCI.class);
		this.englishSellerIBP = new EnglishSellerInboundPortForPlugin(this.englishSellerPlugin_URI, this.getOwner(),
				this.getPluginURI());
		this.englishSellerIBP.publishPort();
	}

	@Override
	public void uninstall() throws Exception {
		this.englishSellerIBP.unpublishPort();
		this.englishSellerIBP.destroyPort();
		this.removeOfferedInterface(EnglishSellerCI.class);
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	@Override
	public boolean sellingRequest(BiddedObject object) throws Exception {
		return this.getOwner().handleRequest(s -> (EnglishSeller) s).sellingRequest(object);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return this.getOwner().handleRequest(s -> (EnglishSeller) s).getListOfGoods();
	}

}
