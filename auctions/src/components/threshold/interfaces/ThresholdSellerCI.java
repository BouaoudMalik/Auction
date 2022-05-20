package components.threshold.interfaces;

import java.util.ArrayList;

import entities.BiddedObject;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Threshold Seller component interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public interface ThresholdSellerCI extends OfferedCI, RequiredCI {

	/**
	 * Method checking if the seller request for the object has been accepted, we
	 * add the object to the list of objects to bid
	 * 
	 * @param object the bid object
	 * @return boolean value
	 * @throws Exception when calling a method from an outbound port
	 */
	public boolean sellingRequest(BiddedObject object) throws Exception;

	/**
	 * Method returning the list of goods
	 * 
	 * @return array list of object
	 * @throws Exception throw an exception
	 */
	public ArrayList<BiddedObject> getListOfGoods() throws Exception;

}
