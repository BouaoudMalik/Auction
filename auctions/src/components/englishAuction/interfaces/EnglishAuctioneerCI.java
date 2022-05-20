package components.englishAuction.interfaces;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import components.englishAuction.EnglishBidder;
import components.interfaces.ProtocolCI;
import entities.BiddedObject;
import entities.Offer;
import entities.ProtocolState;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * English Auctioneer components interface
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public interface EnglishAuctioneerCI extends OfferedCI, RequiredCI, ProtocolCI {

	/**
	 * Method checking if the selling request is accepted or not
	 * 
	 * @param object the object bid for the auction
	 * @return boolean value
	 * @throws Exception when the number of thread is not sufficient
	 */
	public boolean acceptSellingRequest(BiddedObject object) throws Exception;

	/**
	 * Method verifying and add bidders to the list of participation request for an
	 * English bidding
	 * 
	 * @throws Exception exception
	 * @param bidder the bidder
	 * @return boolean value
	 */
	public boolean acceptParticipationRequest(EnglishBidder bidder) throws Exception;

	/**
	 * Method adding an object for an auction
	 * 
	 * @param object the bid object for the auction
	 * @throws Exception exception
	 * 
	 */
	public void addObjectToBid(BiddedObject object) throws Exception;

	/**
	 * Method adding an offer the hash map ranking
	 * 
	 * @param offers concurrent HashMap for the bidders and their offers
	 * @param offer  Offer
	 * @throws Exception when called by an out/inboundPort
	 */
	public void addOffer(ConcurrentHashMap<String, Float> offers, Offer offer) throws Exception;

	/**
	 * Method getting the current number of participants during an auction
	 * 
	 * @return number of participants
	 * @throws Exception exception
	 */
	public int getCurrentParticipants() throws Exception;

	/**
	 * Method getting the list of bidders participating in the English bidding
	 * 
	 * @return array list of bidders
	 * @throws Exception exception
	 */
	public Vector<EnglishBidder> getBidders() throws Exception;

	/**
	 * Method to return the Entry of the maximal offer received
	 * 
	 * @param offers hash map of biddersId and their price
	 * @return Max offer and the bidder
	 * @throws Exception exception
	 */
	public Entry<String, Float> getMaxOffers(ConcurrentHashMap<String, Float> offers) throws Exception;

	/**
	 * Method getting the current price for an item during an auction
	 * 
	 * @param state the protocol state
	 * @return the current price
	 * @throws Exception exception
	 */
	public float getCurrentPrice(ProtocolState state) throws Exception;

	/**
	 * Method setting the new current price during an auction
	 * 
	 * @param currentPrice the new current price
	 * @throws Exception exception
	 */
	public void setCurrentPrice(float currentPrice) throws Exception;

	/**
	 * Method setting the number of participants with the value of
	 * currentParticipants
	 * 
	 * @param currentParticipants number of current participants
	 * @throws Exception exception
	 */
	public void setCurrentParticipants(int currentParticipants) throws Exception;

}
