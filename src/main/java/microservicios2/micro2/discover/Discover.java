package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class Discover implements DicoverInterface {
    @Autowired
    DiscoverCache discoverCache;

    public void sendBroadcast() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        String nowString = dateFormat.format(now);


        BroadcastingClient broadcast = new BroadcastingClient(1);
        //Envia :  ip-controllerUrl-name-date
        broadcast.discoverServers(
                InetAddress.getLocalHost().getHostAddress() +
                "-" + InetAddress.getLocalHost().getHostAddress() + ":8080/" +
                "-" + InetAddress.getLocalHost().getHostName() +
                "-" + nowString
        );
        broadcast.receivePacket();
        broadcast.close();
    }

    @Override
    public void insert(Peer peer) {
        discoverCache.purge();
    }

}
