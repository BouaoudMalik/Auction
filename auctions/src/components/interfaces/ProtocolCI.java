package components.interfaces;

import entities.ProtocolState;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Protocol interface, represent the progress of an English or threshold bidding
 * protocol
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public interface ProtocolCI extends OfferedCI, RequiredCI {

	/**
	 * Method initializing an auction
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling an selling request from an outbound port
	 */
	public void initializeBid(ProtocolState state) throws Exception;

	/**
	 * Method adding participants to the auction
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from an outbound port
	 */
	public void addParticipants(ProtocolState state) throws Exception;

	/**
	 * Method conducting the auction
	 * 
	 * @param state the protocol state
	 * @throws Exception using the semaphore, sleep, or calling a method from an
	 *                   outbound port
	 */
	public void auction(ProtocolState state) throws Exception;

	/**
	 * Method closing the bid
	 * 
	 * @param state the protocol state
	 * @throws Exception using the semaphore, sleep, or calling a method from an
	 *                   outbound port
	 */
	public void closeBid(ProtocolState state) throws Exception;

	/**
	 * Method announcing the winner of the auction with the id and the offer made
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from an outbound port
	 */
	public void announceWinner(ProtocolState state) throws Exception;

	/**
	 * Method announcing the winner of the auction with the id and the offer made
	 * 
	 * @param state the protocol state
	 * @throws Exception when calling a method from an outbound port
	 */
	public void notifySeller(ProtocolState state) throws Exception;

}
