package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.util.calendar.LocalGregorianCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

@Component
public class DiscoverCache implements Cache{
    private ArrayList<Peer> discoveredPeers = new ArrayList<>();

    public void insertPeer(Peer newPeer){
        discoveredPeers.add(newPeer);

        System.out.println("--------------------------------------------------------");
        for (Peer peer : discoveredPeers) {
            System.out.println(peer.toString());
        }
        System.out.println("--------------------------------------------------------");
    }

    @Scheduled(fixedRate =  10 * 1000)
    public void purge(){
        System.out.println(Calendar.getInstance().getTime().getTime());
        for(int peerIndex = 0; peerIndex < discoveredPeers.size(); peerIndex++){
            Peer peer = discoveredPeers.get(peerIndex);
            if(peer.getDate().getTime() < 15*60*1000){
                discoveredPeers.remove(peerIndex);
            }
        }
        System.out.println("purgado");
    }

}
