package pn.eric.rpc.rmi.server;

import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Shadow
 * @date
 */
public class WarehouseServer {
    public static void main(String[] args) throws RemoteException, NamingException, MalformedURLException, java.rmi.AlreadyBoundException {
                 System.out.println("Constructing server implementation");
                 WarehouseImp centralWarehouse = new WarehouseImp();

                 System.out.println("Binding server implementation to registry");
                 LocateRegistry.createRegistry(1099);
                 Naming.bind("rmi://localhost:1099/central_warehoues", centralWarehouse);

                System.out.println("Waiting for invocations from clients ...");
            }
}
