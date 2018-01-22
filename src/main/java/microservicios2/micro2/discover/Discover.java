package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import static microservicios2.micro2.utils.utils.getFirstNonLoopbackAddress;

@Service
public class Discover implements DicoverInterface {
    @Autowired
    private DiscoverCache discoverCache;

    public void sendBroadcast() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date now = Calendar.getInstance().getTime();
        String nowString = dateFormat.format(now);


        BroadcastingClient broadcast = new BroadcastingClient(0);

        //Envia :  ip controllerUrl name date
        broadcast.discoverServers( getFirstNonLoopbackAddress() +
                " http:/" + getFirstNonLoopbackAddress() + ":8080/controller" +
                " " + InetAddress.getLocalHost().getHostName() +
                " " + nowString
        );
        broadcast.close();
    }

    @Override
    public void insert(Peer peer) {
        discoverCache.insertPeer(peer);
    }

    public DiscoverCache getDiscoverCache() {
        return discoverCache;
    }
}
