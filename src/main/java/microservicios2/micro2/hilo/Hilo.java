package microservicios2.micro2.hilo;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class Hilo extends Thread {
    @PostConstruct
    public void run(){
        try {
            BroadcastingEchoServer broadcastingEchoServer = new BroadcastingEchoServer();
            broadcastingEchoServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}