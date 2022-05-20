package cvms;

import java.util.ArrayList;

import components.englishAuction.EnglishAuctioneer;
import components.englishAuction.EnglishBidder;
import components.englishAuction.EnglishSeller;
import components.englishAuction.connections.obp.EnglishBidderOutboundPort;
import entities.BiddedObject;
import entities.Behavior;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * English protocol CVM
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class CVMEnglishBidding extends AbstractCVM {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private static final int NUMBER_OF_BIDDERS = 30;
	private static final float START_BID = (float) 100.0;
	private static final int NUMBER_OF_PARTICIPANTS = 50;
	private static final int NUMBER_OF_THREADS = 10;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public CVMEnglishBidding() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {

		BiddedObject object = new BiddedObject(1, "3000 KW", "3000 Kilos Watts of solar energy", START_BID);
		ArrayList<BiddedObject> listOfGoods = new ArrayList<>();
		listOfGoods.add(object);

		BiddedObject object2 = new BiddedObject(2, "6000 KW", "6000 Kilos Watts of solar energy", START_BID * 2);
		listOfGoods.add(object2);

		BiddedObject object3 = new BiddedObject(3, "9000 KW", "9000 Kilos Watts of solar energy", START_BID * 3);
		listOfGoods.add(object3);

		BiddedObject object4 = new BiddedObject(4, "12000 KW", "10000 Kilos Watts of solar energy", START_BID * 3);
		listOfGoods.add(object4);

		BiddedObject object5 = new BiddedObject(5, "12000 KW", "12000 Kilos Watts of solar energy", START_BID * 3);
		listOfGoods.add(object5);

		ArrayList<String> biddersIbp = new ArrayList<>();
		ArrayList<EnglishBidderOutboundPort> bidders = new ArrayList<>();

		for (int i = 0; i < NUMBER_OF_BIDDERS; i++) {
			String bidderIBP = AbstractPort.generatePortURI();
			biddersIbp.add(bidderIBP);
			AbstractComponent.createComponent(EnglishBidder.class.getCanonicalName(),
					new Object[] { START_BID, START_BID * (i + 1), listOfGoods.get(0), bidderIBP, Behavior.Passive });
			bidderIBP = AbstractPort.generatePortURI();
			biddersIbp.add(bidderIBP);
			AbstractComponent.createComponent(EnglishBidder.class.getCanonicalName(), new Object[] { START_BID,
					2 * START_BID * (i + 2), listOfGoods.get(1), bidderIBP, Behavior.Active });
			bidderIBP = AbstractPort.generatePortURI();
			biddersIbp.add(bidderIBP);
			AbstractComponent.createComponent(EnglishBidder.class.getCanonicalName(), new Object[] { 2 * START_BID,
					3 * START_BID * (i + 3), listOfGoods.get(2), bidderIBP, Behavior.Aggressive });

			bidderIBP = AbstractPort.generatePortURI();
			biddersIbp.add(bidderIBP);
			AbstractComponent.createComponent(EnglishBidder.class.getCanonicalName(),
					new Object[] { START_BID, START_BID * (i + 10), listOfGoods.get(3), bidderIBP, Behavior.Passive });

			bidderIBP = AbstractPort.generatePortURI();
			biddersIbp.add(bidderIBP);
			AbstractComponent.createComponent(EnglishBidder.class.getCanonicalName(),
					new Object[] { START_BID, START_BID * (i + 10), listOfGoods.get(4), bidderIBP, Behavior.Passive });
		}

		AbstractComponent.createComponent(EnglishSeller.class.getCanonicalName(), new Object[] { listOfGoods });
		AbstractComponent.createComponent(EnglishAuctioneer.class.getCanonicalName(),
				new Object[] { bidders, biddersIbp, NUMBER_OF_PARTICIPANTS, NUMBER_OF_THREADS });

		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVMEnglishBidding c = new CVMEnglishBidding();
			c.startStandardLifeCycle(100000L);
			Thread.sleep(1000000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
