package entities;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class illustrating the state of a protocol
 *
 */
public class ProtocolState {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private ProtocolProgress state;
	private BiddedObject biddedObject;
	private ConcurrentHashMap<String, Float> offers;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public ProtocolState(ProtocolProgress state, BiddedObject biddedObject, ConcurrentHashMap<String, Float> offers) {
		this.state = state;
		this.biddedObject = biddedObject;
		this.offers = offers;
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method getting the state of the protocol progression
	 * 
	 * @return the state of the protocol
	 */
	public ProtocolProgress getState() {
		return this.state;
	}

	/**
	 * Method setting the protocol progression state
	 * 
	 * @param state the protocol state during his progression
	 */
	public void setState(ProtocolProgress state) {
		this.state = state;
	}

	/**
	 * Method getting the bid object
	 * 
	 * @return
	 */
	public BiddedObject getBiddedObject() {
		return this.biddedObject;
	}

	/**
	 * Method setting the bid object
	 * 
	 * @param biddedObject the bid object
	 */
	public void setBiddedObject(BiddedObject biddedObject) {
		this.biddedObject = biddedObject;
	}

	/**
	 * Method getting the map of offers
	 * 
	 * @return the offers hash map
	 */
	public ConcurrentHashMap<String, Float> getOffers() {
		return this.offers;
	}

	/**
	 * Method setting the offers hash map
	 * 
	 * @param offers the offers hash map
	 */
	public void setOffers(ConcurrentHashMap<String, Float> offers) {
		this.offers = offers;
	}

	/**
	 * Method to sort the offers in ascending order
	 * 
	 * @param unsortMap the offers hash map
	 * @return a sorted Map
	 */
	public Map<String, Float> sortOffers(ConcurrentHashMap<String, Float> unsortMap) {
		List<Entry<String, Float>> list = new LinkedList<Entry<String, Float>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Float>>() {
			public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		// Maintaining insertion order with the help of LinkedList
		HashMap<String, Float> sortedMap = new LinkedHashMap<String, Float>();
		for (Entry<String, Float> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
