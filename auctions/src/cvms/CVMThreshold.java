package cvms;

import java.util.ArrayList;

import components.threshold.ThresholdAuctioneer;
import components.threshold.ThresholdBidder;
import components.threshold.ThresholdSeller;
import components.threshold.connections.obp.ThresholdBidderOutboundPort;
import entities.BiddedObject;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * Threshold protocol CVM
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class CVMThreshold extends AbstractCVM {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private static final int NUMBER_OF_BIDDERS = 30;
	private static final int START_BID = 100;
	private static final int NUMBER_OF_THREADS = 5;
	private String auctioneerPlugInUri = AbstractPort.generatePortURI();
	private String sellerPortObpUri = AbstractPort.generatePortURI();
	private String sellerPluginUri = "seller-pluginUri";

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public CVMThreshold() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {
		/**
		 * Créer les cps et les connecter
		 * 
		 * nom de la classe du cps; Tab contenant les params, new Object[] si vide
		 * 
		 * ensuite il faut connecter les cps entre eux avec do port connection (sortant
		 * ==> entrant)
		 */
		BiddedObject object = new BiddedObject(1, "3000 KW", "3000 Kilos Watts of solar energy", START_BID);
		ArrayList<BiddedObject> listOfGoods = new ArrayList<>();
		listOfGoods.add(object);
		object = new BiddedObject(2, "6000 KW", "6000 Kilos Watts of solar energy", START_BID * 2);
		listOfGoods.add(object);
		object = new BiddedObject(3, "8000 KW", "8000 Kilos Watts of solar energy", START_BID * 3);
		listOfGoods.add(object);

		AbstractComponent.createComponent(ThresholdSeller.class.getCanonicalName(),
				new Object[] { listOfGoods, sellerPortObpUri, auctioneerPlugInUri, sellerPluginUri });

		ArrayList<String> biddersIBP = new ArrayList<>();

		/**
		 * Bidders
		 */
		for (int i = 0; i < NUMBER_OF_BIDDERS; i++) {
			String bidderIBP = AbstractPort.generatePortURI();
			biddersIBP.add(bidderIBP);
			AbstractComponent.createComponent(ThresholdBidder.class.getCanonicalName(),
					new Object[] { START_BID, START_BID * i, listOfGoods.get(0), bidderIBP, auctioneerPlugInUri });
			String bidderIBP2 = AbstractPort.generatePortURI();
			biddersIBP.add(bidderIBP2);
			AbstractComponent.createComponent(ThresholdBidder.class.getCanonicalName(), new Object[] { START_BID * 2,
					START_BID * (i + 2), listOfGoods.get(1), bidderIBP2, auctioneerPlugInUri });
			String bidderIBP3 = AbstractPort.generatePortURI();
			biddersIBP.add(bidderIBP3);
			AbstractComponent.createComponent(ThresholdBidder.class.getCanonicalName(), new Object[] { START_BID * 3,
					START_BID * (i + 3), listOfGoods.get(2), bidderIBP3, auctioneerPlugInUri });
		}

		ArrayList<ThresholdBidderOutboundPort> bidders = new ArrayList<>();

		AbstractComponent.createComponent(ThresholdAuctioneer.class.getCanonicalName(), new Object[] {
				NUMBER_OF_BIDDERS, bidders, biddersIBP, NUMBER_OF_THREADS, auctioneerPlugInUri, sellerPluginUri });

		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVMThreshold c = new CVMThreshold();
			c.startStandardLifeCycle(100000L);
			Thread.sleep(100000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
