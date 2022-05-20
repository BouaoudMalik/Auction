package components.englishAuction.connections.ibp;

import java.util.ArrayList;

import components.englishAuction.EnglishSeller;
import components.englishAuction.interfaces.EnglishSellerCI;
import entities.BiddedObject;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * English seller inbound port
 * 
 * @author Malik Bouaoud and Sandrine Ear
 *
 */
public class EnglishSellerInboundPort extends AbstractInboundPort implements EnglishSellerCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Creating the inbound port instance with the URI
	 * 
	 * @param uri   the unique identifier of the port
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishSellerInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, EnglishSellerCI.class, owner);
		assert uri != null && !uri.isEmpty();
	}

	/**
	 * Creating the inbound port instance
	 * 
	 * @param owner the owner of the component owning the port
	 * @throws Exception
	 * 
	 */
	public EnglishSellerInboundPort(ComponentI owner) throws Exception {
		super(EnglishSellerCI.class, owner);
	}

	@Override
	public boolean sellingRequest(BiddedObject objectBidded) throws Exception {
		return this.getOwner().handleRequest(s -> (EnglishSeller) s).sellingRequest(objectBidded);
	}

	@Override
	public ArrayList<BiddedObject> getListOfGoods() throws Exception {
		return this.getOwner().handleRequest(s -> (EnglishSeller) s).getListOfGoods();
	}

}
