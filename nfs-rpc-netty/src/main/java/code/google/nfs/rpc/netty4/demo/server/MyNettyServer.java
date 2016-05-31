package code.google.nfs.rpc.netty4.demo.server;

import code.google.nfs.rpc.NamedThreadFactory;
import code.google.nfs.rpc.netty4.server.NettyServer;
import code.google.nfs.rpc.protocol.RPCProtocol;
import code.google.nfs.rpc.server.Server;

import java.util.concurrent.*;

/**
 * Created by shangri_la on 15/5/18.
 */
public class MyNettyServer {
    public static void main(String[] args) {
        final Server server = new NettyServer();
        server.registerProcessor(RPCProtocol.TYPE, "sayHello", new HelloWorldComponent());
        server.registerProcessor(RPCProtocol.TYPE, "sayHi", new HelloWorldComponent());
        ThreadFactory tf = new NamedThreadFactory("BUSINESSTHREADPOOL");

        //构建线程池
        ExecutorService threadPool = new ThreadPoolExecutor(20, 100, 300, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                tf, new ThreadPoolExecutor.AbortPolicy());
        try {
            server.start(18889, threadPool);
            System.out.println("server starting....................");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
