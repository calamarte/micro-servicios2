package microservicios2.micro2.hilo;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Hilo {

    public void run(){
        try {
            BroadcastingEchoServer broadcastingEchoServer = new BroadcastingEchoServer();
            broadcastingEchoServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}