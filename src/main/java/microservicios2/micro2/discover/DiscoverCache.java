package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;

@Component
public class DiscoverCache {
    private ArrayList<Peer> discoveredPeers;

    public void insertPeer(Peer newPeer){
        discoveredPeers.add(newPeer);
    }

    public void purgue(){
        for(int peerIndex = 0; peerIndex < discoveredPeers.size(); peerIndex++){
            Peer peer = discoveredPeers.get(peerIndex);
            if(peer.getDate().getTime() < 15*60*1000){
                discoveredPeers.remove(peerIndex);
            }
        }
    }

}
