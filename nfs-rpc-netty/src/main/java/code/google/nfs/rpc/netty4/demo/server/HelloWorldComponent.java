package code.google.nfs.rpc.netty4.demo.server;

/**
 * Created by shangri_la on 15/5/18.
 */
public class HelloWorldComponent implements HelloWorldService {


    @Override
    public String sayHello(String word) {

        return word + " return by server";
    }
}
