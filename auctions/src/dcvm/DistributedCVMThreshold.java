package dcvm;

import java.util.ArrayList;

import components.threshold.ThresholdAuctioneer;
import components.threshold.ThresholdBidder;
import components.threshold.ThresholdSeller;
import components.threshold.connections.connectors.ThresholdAuctioneerConnector;
import components.threshold.connections.obp.ThresholdBidderOutboundPort;
import entities.BiddedObject;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
import fr.sorbonne_u.components.helpers.CVMDebugModes;

public class DistributedCVMThreshold extends AbstractDistributedCVM {

	private static final int NUMBER_OF_BIDDERS = 30;
	private static final int START_BID = 100;
	private static final int NUMBER_OF_THREADS = 5;

	public DistributedCVMThreshold(String[] args, int xLayout, int yLayout) throws Exception {
		super(args, xLayout, yLayout);
	}

	public String JVMURI1 = "JVM1";
	public String JVMURI2 = "JVM2";

	String auctioneerPlugInUri = "auctioneer-uri";
	String sellerPlugInUri = "auctioneer-pluguri";

	String sellerObp = "seller-uri";

	@Override
	public void initialise() throws Exception {
		super.initialise();
		String[] jvmURIs = this.configurationParameters.getJvmURIs();
		boolean JVMURI1_OK = false;
		boolean JVMURI2_OK = false;

		for (int i = 0; i < jvmURIs.length && (!JVMURI1_OK || !JVMURI2_OK); i++) {
			if (jvmURIs[i].equals(JVMURI1)) {
				JVMURI1_OK = true;
			} else if (jvmURIs[i].equals(JVMURI2)) {
				JVMURI2_OK = true;
			}

		}

	}

	// création des composants

	@Override
	public void instantiateAndPublish() throws Exception {

		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.LIFE_CYCLE);
		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.INTERFACES);
		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PORTS);
		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING);
		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CALLING);
		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.EXECUTOR_SERVICES);

		BiddedObject object = new BiddedObject(1, "3000 KW", "3000 Kilos Watts of solar energy", START_BID);
		ArrayList<BiddedObject> listOfGoods = new ArrayList<>();
		listOfGoods.add(object);
		object = new BiddedObject(2, "6000 KW", "6000 Kilos Watts of solar energy", START_BID * 2);
		listOfGoods.add(object);
		object = new BiddedObject(3, "8000 KW", "8000 Kilos Watts of solar energy", START_BID * 3);
		listOfGoods.add(object);
		ArrayList<ThresholdBidderOutboundPort> bidders = new ArrayList<>();
		ArrayList<String> biddersIBP = new ArrayList<>();

		if (AbstractCVM.getThisJVMURI().equals(JVMURI1)) {
			AbstractComponent.createComponent(ThresholdSeller.class.getCanonicalName(),
					new Object[] { listOfGoods, sellerObp, auctioneerPlugInUri, sellerPlugInUri });
		} else if (AbstractCVM.getThisJVMURI().equals(JVMURI2)) {

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
				AbstractComponent.createComponent(ThresholdBidder.class.getCanonicalName(), new Object[] {
						START_BID * 2, START_BID * (i + 2), listOfGoods.get(1), bidderIBP2, auctioneerPlugInUri });
				String bidderIBP3 = AbstractPort.generatePortURI();
				biddersIBP.add(bidderIBP3);
				AbstractComponent.createComponent(ThresholdBidder.class.getCanonicalName(), new Object[] {
						START_BID * 3, START_BID * (i + 3), listOfGoods.get(2), bidderIBP3, auctioneerPlugInUri });
			}

			AbstractComponent.createComponent(ThresholdAuctioneer.class.getCanonicalName(), new Object[] {
					NUMBER_OF_BIDDERS, bidders, biddersIBP, NUMBER_OF_THREADS, auctioneerPlugInUri, sellerPlugInUri });

		}
		super.instantiateAndPublish();
	}

	@Override
	public void shutdown() throws Exception {
		super.shutdown();
	}

	public static void main(String[] args) {
		try {
			DistributedCVMThreshold d;
			d = new DistributedCVMThreshold(args, 2, 5);
			d.startStandardLifeCycle(100000L);
			Thread.sleep(100000L);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
}
