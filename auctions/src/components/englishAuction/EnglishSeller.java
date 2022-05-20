package components.englishAuction;

import java.util.ArrayList;

import components.englishAuction.connections.connectors.EnglishAuctioneerConnector;
import components.englishAuction.connections.obp.EnglishAuctioneerOutboundPort;
import components.englishAuction.interfaces.EnglishAuctioneerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import plugins.englishAuction.EnglishAuctioneerPlugin;
import plugins.englishAuction.EnglishSellerPlugin;

/**
 * Class that represent an English seller component, this class is supposed to
 * allow send a selling request to the seller, and to be informed of the result
 * of the auction
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
@RequiredInterfaces(required = { EnglishAuctioneerCI.class })
public class EnglishSeller extends AbstractComponent {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------
	/**
	 * uri for the seller
	 */
	public static String sellerURI = AbstractPort.generatePortURI();

	protected EnglishAuctioneerOutboundPort englishAuctioneerOutboundPort;

	// Plugin

	protected EnglishSellerPlugin englishSellerPlugin;
	/**
	 * uri for the plugin seller
	 */
	public String seller_plugin_URI = "seller-plugin-uri";

	private ArrayList<BiddedObject> listOfGoods;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	protected EnglishSeller(ArrayList<BiddedObject> listOfGoods) throws Exception {
		super(1, 0);

		// Plugin seller
		this.englishSellerPlugin = new EnglishSellerPlugin();
		this.englishSellerPlugin.setPluginURI(seller_plugin_URI + AbstractPort.generatePortURI());
		this.installPlugin(englishSellerPlugin);

		this.englishAuctioneerOutboundPort = new EnglishAuctioneerOutboundPort(this);
		this.englishAuctioneerOutboundPort.publishPort();

		this.listOfGoods = listOfGoods;
	}

	// -------------------------------------------------------------------------
	// Component life-cycle
	// -------------------------------------------------------------------------

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();
		try {
			this.doPortConnection(this.englishAuctioneerOutboundPort.getPortURI(),
					EnglishAuctioneerPlugin.englishAuctioneerPlugin_URI,
					EnglishAuctioneerConnector.class.getCanonicalName());
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
		this.doPortDisconnection(this.englishAuctioneerOutboundPort.getPortURI());

		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.englishAuctioneerOutboundPort.unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * If the seller request for the object has been accepted, we add the object to
	 * the list of object to bid
	 * 
	 * @param object the bid object
	 * @return boolean value
	 * @throws Exception when calling a method from an outbound port
	 */
	public boolean sellingRequest(BiddedObject object) throws Exception {
		if (this.englishAuctioneerOutboundPort.acceptSellingRequest(object)) {
			this.englishAuctioneerOutboundPort.addObjectToBid(object);
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
