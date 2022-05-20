package plugins.threshold.ports;

import java.util.ArrayList;

import components.threshold.ThresholdSeller;
import components.threshold.interfaces.ThresholdSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * Threshold Seller inbound port for plugin
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class ThresholdSellerInboundPortForPlugin extends AbstractInboundPort implements ThresholdSellerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the inbound port instance with the URI
	 * 
	 * @param uri       the unique identifier of the port
	 * @param owner     the owner of the component owning the port
	 * @param pluginURI the unique identifier of the port with plugin
	 * @throws Exception
	 * 
	 */
	public ThresholdSellerInboundPortForPlugin(String uri, ComponentI owner, String pluginURI) throws Exception {
		super(uri, ThresholdSellerCI.class, owner, pluginURI, null);
		assert uri != null && !uri.isEmpty();
	}

	/**
	 * Creating the inbound port instance
	 * 
	 * @param owner     the owner of the component owning the port
	 * @param pluginURI the unique identifier of the port with plugin
	 * @throws Exception
	 * 
	 */
	public ThresholdSellerInboundPortForPlugin(ComponentI owner, String pluginURI) throws Exception {
		super(ThresholdSellerCI.class, owner, pluginURI, null);
	}

	@Override
	public boolean sellingRequest(BiddedObject object) throws Exception {
		return this.getOwner().handleRequest(s -> (ThresholdSeller) s).sellingRequest(object);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return this.getOwner().handleRequest(s -> (ThresholdSeller) s).getListOfGoods();
	}

}
