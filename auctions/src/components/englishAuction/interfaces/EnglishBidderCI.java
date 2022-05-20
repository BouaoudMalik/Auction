package components.englishAuction.interfaces;

import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * English Bidder components interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public interface EnglishBidderCI extends OfferedCI, RequiredCI {

	/**
	 * Method that send a participation request to the auction
	 * 
	 * @return a response, true if the participation is accepted false else
	 * @throws Exception when calling a method from an outbound port
	 */
	public boolean participationRequest() throws Exception;

	/**
	 * Method that represent the willing of the bidder in making an offer
	 * 
	 * @param state the protocol state
	 * @return boolean true if the bidder wants to submit an offer, else false
	 * @throws Exception when calling a method from an outbound port
	 */
	public boolean wantToMakeAnOffer(ProtocolState state) throws Exception;

	/**
	 * Generate a pertinent offer according to the type of the bidder
	 * 
	 * @param state the protocol state
	 * @return the proposed price
	 * @throws Exception when calling a method from an outbound port
	 */
	public float generatePertinentOffer(ProtocolState state) throws Exception;

	/**
	 * Method that return an offer
	 * 
	 * @param object     the concerned object
	 * @param priceOffer the price
	 * @return Offer an offer
	 * @throws Exception throw an exception
	 * 
	 */
	public Offer makeAnOffer(BiddedObject object, float priceOffer) throws Exception;

	/**
	 * This retrieve method update the number of the participant
	 * 
	 * @throws Exception when calling a method from an outbound port
	 */
	public void retrieve() throws Exception;

	/**
	 * Method that test if a bidder can afford a object with it price
	 * 
	 * @param price the price of the concerned object
	 * @return boolean return if the price is included between rangeMin and Max
	 * @throws Exception throw an exception
	 * 
	 */
	public boolean isInRange(float price) throws Exception;

	/**
	 * Method to get the bidder's name
	 * 
	 * @return BidderId
	 * @throws Exception throw an exception
	 * 
	 */
	public String getBidderId() throws Exception;

	/**
	 * Method to get the bid object interested by a bidder
	 * 
	 * @return bid object
	 * @throws Exception throw an exception
	 * 
	 */
	public BiddedObject getObjectInterestedIn() throws Exception;

}
