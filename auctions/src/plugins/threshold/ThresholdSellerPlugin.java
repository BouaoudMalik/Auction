package plugins.threshold;

import java.util.ArrayList;

import components.threshold.ThresholdSeller;
import components.threshold.interfaces.ThresholdSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import plugins.threshold.ports.ThresholdSellerInboundPortForPlugin;

/**
 * Threshold Seller plugin class
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdSellerPlugin extends AbstractPlugin implements ThresholdSellerCI {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected ThresholdSellerInboundPortForPlugin thresholdSellerIBP;
	public String thresholdSellerPlugin_URI;
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public ThresholdSellerPlugin(String thresholdSellerPlugin_URI) throws Exception {
		super();
		this.thresholdSellerPlugin_URI = thresholdSellerPlugin_URI;
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

		this.addOfferedInterface(ThresholdSellerCI.class);
		this.thresholdSellerIBP = new ThresholdSellerInboundPortForPlugin(this.thresholdSellerPlugin_URI,
				this.getOwner(), this.getPluginURI());
		this.thresholdSellerIBP.publishPort();
	}

	@Override
	public void uninstall() throws Exception {
		this.thresholdSellerIBP.unpublishPort();
		this.thresholdSellerIBP.destroyPort();
		this.removeOfferedInterface(ThresholdSellerCI.class);
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	@Override
	public boolean sellingRequest(BiddedObject object) throws Exception {
		return this.getOwner().handleRequest(s -> (ThresholdSeller) s).sellingRequest(object);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return this.getOwner().handleRequest(s -> (ThresholdSeller) s).getListOfGoods();
	}

}