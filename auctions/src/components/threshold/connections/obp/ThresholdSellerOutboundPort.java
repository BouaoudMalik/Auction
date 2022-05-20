package components.threshold.connections.obp;

import java.util.ArrayList;

import components.threshold.interfaces.ThresholdSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * Threshold seller outbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdSellerOutboundPort extends AbstractOutboundPort implements ThresholdSellerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the outbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdSellerOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ThresholdSellerCI.class, owner);
	}

	/**
	 * Creating the outbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdSellerOutboundPort(ComponentI owner) throws Exception {
		super(ThresholdSellerCI.class, owner);
	}

	@Override
	public boolean sellingRequest(BiddedObject objectBidded) throws Exception {
		return ((ThresholdSellerCI) this.getConnector()).sellingRequest(objectBidded);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return ((ThresholdSellerCI) this.getConnector()).getListOfGoods();
	}

}
