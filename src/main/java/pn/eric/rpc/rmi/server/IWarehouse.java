package pn.eric.rpc.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Shadow
 * @date 2016/9/29 19:40
 */
public interface IWarehouse extends Remote {
     double getPrice(String description) throws RemoteException;
}