package components.threshold.connections.connectors;

import java.util.ArrayList;

import components.threshold.interfaces.ThresholdSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * Threshold seller connector
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdSellerConnector extends AbstractConnector implements ThresholdSellerCI {

	@Override
	public boolean sellingRequest(BiddedObject objectBidded) throws Exception {
		return ((ThresholdSellerCI) this.offering).sellingRequest(objectBidded);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return ((ThresholdSellerCI) this.offering).getListOfGoods();
	}

}
