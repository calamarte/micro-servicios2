package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import static microservicios2.micro2.utils.Utils.getFirstNonLoopbackAddress;

@Service
public class Discover implements DicoverInterface {
    @Value("${peersListenerPath}")
    private String controller;

    @Value("${server.port}")
    private int port = 8080;

    @Autowired
    private DiscoverCache discoverCache;


    public void sendBroadcast() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date now = Calendar.getInstance().getTime();
        String nowString = dateFormat.format(now);
        BroadcastingClient broadcast = new BroadcastingClient(0);

        /* IP + ControllerURL:8080 + Name + Date */
        broadcast.discoverServers( getFirstNonLoopbackAddress() +
                " http:/" + getFirstNonLoopbackAddress() + ":"+port+"/" + controller +
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
