package entities;

import java.io.Serializable;

/**
 * The bidded object for the auction
 *
 */
public class BiddedObject implements Serializable {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	private String name;
	private String description;
	private int objectId;
	private float priceReference; // The reference price can be the min or the max price for the bidded object
	private boolean isSelled;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public BiddedObject(int objectId, String name, String description, float priceReference) {
		this.objectId = objectId;
		this.name = name;
		this.description = description;
		this.priceReference = priceReference;
		this.isSelled = false;
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * Method getting the name of the object
	 * 
	 * @return the name of the object
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method setting the name of the bid object
	 * 
	 * @param name the name of the object
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method getting the description of the object
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Method setting the description
	 * 
	 * @param description the description of the bidded object
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method getting the price reference for the object
	 * 
	 * @return the float value of the price reference
	 */
	public float getPriceReference() {
		return this.priceReference;
	}

	/**
	 * Method getting the id of the object
	 * 
	 * @return the id of the bid object
	 */
	public int getObjectId() {
		return this.objectId;
	}

	/**
	 * Method returning if a bid object is sold or not
	 * 
	 * @return boolean value
	 */
	public boolean isSelled() {
		return this.isSelled;
	}

	/**
	 * Method setting the boolean isSelled
	 * 
	 * @param isSelled boolean value
	 */
	public void setSelled(boolean isSelled) {
		this.isSelled = isSelled;
	}

}
