package components.threshold.connections.ibp;

import java.util.ArrayList;

import components.threshold.ThresholdSeller;
import components.threshold.interfaces.ThresholdSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * Threshold seller inbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdSellerInboundPort extends AbstractInboundPort implements ThresholdSellerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the inbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdSellerInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ThresholdSellerCI.class, owner);
		assert uri != null && !uri.isEmpty();
	}

	/**
	 * Creating the inbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public ThresholdSellerInboundPort(ComponentI owner) throws Exception {
		super(ThresholdSellerCI.class, owner);
	}

	@Override
	public boolean sellingRequest(BiddedObject objectBidded) throws Exception {
		return this.getOwner().handleRequest(s -> (ThresholdSeller) s).sellingRequest(objectBidded);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return this.getOwner().handleRequest(s -> (ThresholdSeller) s).getListOfGoods();
	}

}
