package pn.eric.rpc.rmiiiop.server;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

/**
 * @author Shadow
 * @date
 * http://lavasoft.blog.51cto.com/62575/240666/
 */
public class HelloImpl extends PortableRemoteObject implements  IHello {
    /**
     * Initializes the object by calling <code>exportObject(this)</code>.
     *
     * @throws RemoteException if export fails.
     */
    public  HelloImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
         return "Hello, " + name + "!";
    }
}
