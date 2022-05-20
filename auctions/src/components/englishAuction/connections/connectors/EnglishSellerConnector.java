package components.englishAuction.connections.connectors;

import java.util.ArrayList;

import components.englishAuction.interfaces.EnglishSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * English seller connector
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishSellerConnector extends AbstractConnector implements EnglishSellerCI {

	@Override
	public boolean sellingRequest(BiddedObject objectBidded) throws Exception {
		return ((EnglishSellerCI) this.offering).sellingRequest(objectBidded);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return ((EnglishSellerCI) this.offering).getListOfGoods();
	}

}
