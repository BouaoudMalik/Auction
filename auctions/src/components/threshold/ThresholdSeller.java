package components.threshold;

import java.util.ArrayList;

import components.threshold.connections.connectors.ThresholdAuctioneerConnector;
import components.threshold.connections.obp.ThresholdAuctioneerOutboundPort;
import components.threshold.interfaces.ThresholdAuctioneerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import plugins.threshold.ThresholdSellerPlugin;

/**
 * Threshold auction seller components interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
@RequiredInterfaces(required = { ThresholdAuctioneerCI.class })
public class ThresholdSeller extends AbstractComponent {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected ThresholdAuctioneerOutboundPort thresholdAuctioneerOutboundPort;
	protected String thresholdAuctioneerPlugin_URI;
	protected String sellerUriObp;

	// Plugin
	protected ThresholdSellerPlugin thresholdSellerPlugin;
	public String seller_plugin_URI;

	private ArrayList<BiddedObject> listOfGoods;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	protected ThresholdSeller(ArrayList<BiddedObject> listOfGoods, String sellerUriObp,
			String thresholdAuctioneerPlugin_URI, String seller_plugin_URI) throws Exception {
		super(1, 0);

		// Plugin seller
		this.thresholdSellerPlugin = new ThresholdSellerPlugin(seller_plugin_URI);
		this.thresholdSellerPlugin.setPluginURI(seller_plugin_URI + AbstractPort.generatePortURI());
		this.installPlugin(this.thresholdSellerPlugin);

		this.thresholdAuctioneerOutboundPort = new ThresholdAuctioneerOutboundPort(sellerUriObp, this);
		this.thresholdAuctioneerOutboundPort.publishPort();

		this.thresholdAuctioneerPlugin_URI = thresholdAuctioneerPlugin_URI;
		this.seller_plugin_URI = seller_plugin_URI;

		this.listOfGoods = listOfGoods;
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();

		try {
			this.doPortConnection(this.thresholdAuctioneerOutboundPort.getPortURI(), this.thresholdAuctioneerPlugin_URI,
					ThresholdAuctioneerConnector.class.getCanonicalName());

		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public synchronized void execute() throws Exception {
		super.execute();
	}

	@Override
	public synchronized void finalise() throws Exception {
		this.doPortDisconnection(this.thresholdAuctioneerOutboundPort.getPortURI());

		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.thresholdAuctioneerOutboundPort.unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method checking if the seller request for the object has been accepted, we
	 * add the object to the list of objects to bid
	 * 
	 * @param object the bid object
	 * @return boolean value
	 * @throws Exception when calling a method from an outbound port
	 */
	public boolean sellingRequest(BiddedObject object) throws Exception {
		if (object != null) {
			System.out.println("A selling request of " + object.getDescription() + " has been sent.");
			this.thresholdAuctioneerOutboundPort.acceptSellingRequest(object);
			return true;
		}
		return false;
	}

	/**
	 * Method returning the list of goods
	 * 
	 * @return array list of object
	 */
	public ArrayList<BiddedObject> getListOfGoods() {
		return this.listOfGoods;
	}

}
