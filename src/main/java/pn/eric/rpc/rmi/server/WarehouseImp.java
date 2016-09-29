package pn.eric.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shadow
 * @date
 */
public class WarehouseImp extends UnicastRemoteObject implements IWarehouse {

    private static final long serialVersionUID = 1L;
    private Map<String,Double> prices;
    protected WarehouseImp() throws RemoteException
    {
        prices = new HashMap<String,Double>();
        prices.put("mate7", 3700.00);
        prices.put("mate5", 1000.00);
    }
    public double getPrice(String description) throws RemoteException
    {
        Double price = prices.get(description);
        return price == null? 0 : price;
    }
}
