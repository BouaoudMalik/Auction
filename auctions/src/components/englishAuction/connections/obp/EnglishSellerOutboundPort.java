package components.englishAuction.connections.obp;

import java.util.ArrayList;

import components.englishAuction.interfaces.EnglishSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * English seller outbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishSellerOutboundPort extends AbstractOutboundPort implements EnglishSellerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the outbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishSellerOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, EnglishSellerCI.class, owner);
	}

	/**
	 * Creating the outbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishSellerOutboundPort(ComponentI owner) throws Exception {
		super(EnglishSellerCI.class, owner);
	}

	@Override
	public boolean sellingRequest(BiddedObject objectBidded) throws Exception {
		return ((EnglishSellerCI) this.getConnector()).sellingRequest(objectBidded);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return ((EnglishSellerCI) this.getConnector()).getListOfGoods();
	}

}
