package code.google.nfs.rpc.netty4.demo.client;

import code.google.nfs.rpc.Codecs;
import code.google.nfs.rpc.netty4.client.NettyClientInvocationHandler;
import code.google.nfs.rpc.netty4.demo.server.HelloWorldService;
import code.google.nfs.rpc.protocol.RPCProtocol;

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
        String result =  null;

        for (int i = 0; i < 100; i++) {

            result =  helloWorldService.sayHello("hello beijing");
            System.out.println(result);

        }
    }

    public HelloWorldService getService(){

        Map<String, Integer> methodTimeouts = new HashMap<String, Integer>();
        methodTimeouts.put("*", 200);

        List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
        servers.add(new InetSocketAddress("127.0.0.1", 18888));

        int codectype = Codecs.HESSIAN_CODEC;

        HelloWorldService service = (HelloWorldService) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{HelloWorldService.class},
                new NettyClientInvocationHandler(servers, 2 , 2000, "sayHello", methodTimeouts, codectype, RPCProtocol.TYPE));

        return  service;
    }


}
