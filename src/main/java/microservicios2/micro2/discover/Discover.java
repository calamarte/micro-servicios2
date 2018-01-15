package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class Discover implements DicoverInterface {
    @Autowired
    DiscoverCache discoverCache;

    public void sendBroadcast() throws Exception {
        BroadcastingClient broadcast = new BroadcastingClient(1);
        broadcast.discoverServers("{" +
                "ip: " + InetAddress.getLocalHost().getHostAddress() +
                ",name: " + InetAddress.getLocalHost().getHostName() +
                ",controllerUrl: " + InetAddress.getLocalHost().getHostAddress() + ":8080/" +
                //Enviar el date de ahora en forma de string. Luego hay que pasarlo de String a Date
                ",date: 300" +
        "}");
        broadcast.receivePacket();
        broadcast.close();
    }

    @Override
    public void insert(Peer peer) {
        discoverCache.purge();
    }

}
