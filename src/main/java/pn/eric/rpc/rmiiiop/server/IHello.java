package pn.eric.rpc.rmiiiop.server;

import java.rmi.RemoteException;

/**
 * @author Shadow
 * @date 2016/9/29 19:53
 */
public interface IHello {
    /**
     * 远程接口方法实现
     *
     * @param name 问候的人名
     * @return 问候语
     */
    String sayHello(String name) throws RemoteException ;
}
