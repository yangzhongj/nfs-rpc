package code.google.nfs.rpc.netty4.demo.client;

import code.google.nfs.rpc.Codecs;
import code.google.nfs.rpc.netty4.client.NettyClientInvocationHandler;
import code.google.nfs.rpc.netty4.demo.server.HelloWorldService;
import code.google.nfs.rpc.protocol.RPCProtocol;

import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shangri_la on 15/5/18.
 */
public class Client {

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new Client().getService();
        for (int i = 0; i < 1; i++) {
            System.out.println(helloWorldService.sayHello("hello beijing " + i));
        }
    }

    public HelloWorldService getService(){

        Map<String, Integer> methodTimeouts = new HashMap<String, Integer>();
        methodTimeouts.put("*", 200000);

        List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
        servers.add(new InetSocketAddress("127.0.0.1", 18889));

        int codecType = Codecs.HESSIAN_CODEC;

        HelloWorldService service = (HelloWorldService) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{HelloWorldService.class},
                new NettyClientInvocationHandler(servers, 3 , 2000, "sayHi", methodTimeouts, codecType, RPCProtocol.TYPE));

        return  service;
    }


}
