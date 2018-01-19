package microservicios2.micro2.hilo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class Hilo implements DisposableBean, Runnable {


    @PostConstruct
    public void run(){
        try {
            BroadcastingEchoServer broadcastingEchoServer = new BroadcastingEchoServer();
            broadcastingEchoServer.run();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void destroy(){

    }

}