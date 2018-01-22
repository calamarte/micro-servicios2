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

@Service
public class Discover implements DicoverInterface {

    @Autowired
    private DiscoverCache discoverCache;

    public void sendBroadcast() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date now = Calendar.getInstance().getTime();
        String nowString = dateFormat.format(now);
        BroadcastingClient broadcast = new BroadcastingClient(0);

        /* IP + ControllerURL:8080 + Name + Date */
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

    private String getFirstNonLoopbackAddress() throws SocketException {
        Enumeration enumerationInterfaces = NetworkInterface.getNetworkInterfaces();
        while (enumerationInterfaces.hasMoreElements()) {
            NetworkInterface anInterface = (NetworkInterface) enumerationInterfaces.nextElement();
            for (Enumeration enumerationAddresses = anInterface.getInetAddresses(); enumerationAddresses.hasMoreElements();) {
                InetAddress addr = (InetAddress) enumerationAddresses.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        return addr.toString();
                    }

                }
            }
        }
        return null;
    }

}
