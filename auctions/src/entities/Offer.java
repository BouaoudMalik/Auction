package entities;

import java.io.Serializable;

/**
 * The offer characteristics made during an auction
 *
 */
public class Offer implements Serializable {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private String bidderId;
	private BiddedObject biddedObject;
	private float priceOffer;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	public Offer(String bidderId, BiddedObject biddedObject, float priceOffer) {
		this.bidderId = bidderId;
		this.biddedObject = biddedObject;
		this.priceOffer = priceOffer;
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method returning the id of the bidder
	 * 
	 * @return string id
	 */
	public String getBidderId() {
		return this.bidderId;
	}

	/**
	 * Method getting the bid object
	 * 
	 * @return the bid object
	 */
	public BiddedObject getBiddedObject() {
		return this.biddedObject;
	}

	/**
	 * Method getting the price offer
	 * 
	 * @return the price offer
	 */
	public float getPriceOffer() {
		return this.priceOffer;
	}

}
