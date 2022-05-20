package components.threshold.interfaces;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import components.interfaces.ProtocolCI;
import components.threshold.ThresholdBidder;
import entities.BiddedObject;
import entities.Offer;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Threshold Auctioneer component interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public interface ThresholdAuctioneerCI extends OfferedCI, RequiredCI, ProtocolCI {

	/**
	 * Method checking if the selling request is accepted or not
	 * 
	 * @param object the bid object for the auction
	 * @return boolean value
	 * @throws Exception throw an exception
	 */
	public boolean acceptSellingRequest(BiddedObject object) throws Exception;

	/**
	 * Method verifying and add bidders to the list of participation request for an
	 * threshold bidding
	 * 
	 * @param bidder the bidder
	 * @return boolean value
	 * @throws Exception throw an exception
	 */
	public boolean acceptParticipationRequest(ThresholdBidder bidder) throws Exception;

	/**
	 * Method adding the offer in the hash map with the id of the bidder associated
	 * with the price offered
	 * 
	 * @param offers    the offers hash map
	 * @param offerMade the offer made by a bidder
	 * @throws Exception throw an exception
	 */
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offerMade) throws Exception;

	/**
	 * Method getting the list of bidders participating in the threshold bidding
	 * 
	 * @return array list of bidders
	 * @throws Exception throw an exception
	 */
	public Vector<ThresholdBidder> getBidders() throws Exception;

}
