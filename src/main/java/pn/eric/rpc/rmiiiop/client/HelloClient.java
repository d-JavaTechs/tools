package pn.eric.rpc.rmiiiop.client;

import org.apache.hadoop.yarn.webapp.example.HelloWorld;
import pn.eric.rpc.rmiiiop.server.IHello;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

/**
 * @author Shadow
 * @date
 */
public class HelloClient {
    public static void main(String[] args) {
        try {
            //获取一个名称上下文
            Context ic = new InitialContext();

            //得到一个远程对象的引用
            Object objRef = ic.lookup("HelloImpl");

            //强制转换为一个更具体的Hello接口对象
            IHello hello = (IHello) PortableRemoteObject.narrow(objRef, HelloWorld.Hello.class);

            // 调用远程对象的方法
            System.out.println("收到来自服务器的消息： " + hello.sayHello("杜武鹏") + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
