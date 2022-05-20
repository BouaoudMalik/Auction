package components.threshold.interfaces;

import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Threshold Bidder component interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public interface ThresholdBidderCI extends OfferedCI, RequiredCI {

	/**
	 * Method checking the participation request for an auction
	 * 
	 * @return boolean value
	 * @throws Exception when calling a method from outbound port
	 */
	public boolean participationRequest() throws Exception;

	/**
	 * Method to make a bid for an object
	 * 
	 * @param object the bid object
	 * @return an offer
	 * @throws Exception when the bidder is not participating
	 */
	public Offer makeAnOffer(BiddedObject object) throws Exception;

	/**
	 * Method indicating that the bidder is no longer participating to the auction
	 * 
	 * @throws Exception throw an exception
	 */
	public void retrieve() throws Exception;

	/**
	 * Method verifying the price range is between the minimum and the maximum range
	 * 
	 * @param price the bid price
	 * @return boolean value
	 * @throws Exception throw an exception
	 */
	public boolean isInRange(float price) throws Exception;

	/**
	 * Method getting the bidder id
	 * 
	 * @return bidder id
	 * @throws Exception throw an exception
	 */
	public String getBidderId() throws Exception;

	/**
	 * Method getting the bid object interested by a bidder
	 * 
	 * @return bid object
	 * @throws Exception throw an exception
	 */
	public BiddedObject getObjectInterestedIn() throws Exception;

}
