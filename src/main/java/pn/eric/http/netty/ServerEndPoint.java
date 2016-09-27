package pn.eric.http.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author Shadow
 * @date
 */
public class ServerEndPoint {
    static Selector selector;
    private static void initServer(int port) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {

            while (true){
                selector.select();
                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    SelectionKey selectionKey = (SelectionKey)it.next();
                    it.remove();
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        channel.write(ByteBuffer.wrap(new String("向客户端发送了一条信息").getBytes()));
                        channel.register(selector,SelectionKey.OP_READ);
                    }else if (selectionKey.isReadable()) {
                        read(selectionKey);
                    }
                }
            }
    }

    public void read(SelectionKey key) throws IOException{
        SocketChannel  socketChannel  = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        socketChannel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端收到信息："+msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());

        socketChannel.write(outBuffer);// 将消息回送给客户端
    }


    public static void main(String[] args) throws Exception {
        ServerEndPoint server = new ServerEndPoint();
        server.initServer(8000);
        server.listen();
    }
}
