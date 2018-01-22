package microservicios2.micro2.discover;

import com.sun.javafx.util.Logging;
import microservicios2.micro2.controller.Peer;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Calendar;
import org.slf4j.Logger;


@Component
public class DiscoverCache implements Cache{
    private ArrayList<Peer> discoveredPeers = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(DiscoverCache.class);

    public void insertPeer(Peer newPeer){
        System.out.println("--------------------------------------------------------");
        for (Peer peer : discoveredPeers) {
            logger.info("Peer found");
            logger.info("Info peer: \n" + peer.toString());
        }
        System.out.println("--------------------------------------------------------");
        if(exist(newPeer.getIp())){
            update(newPeer);
            return;
        }
        discoveredPeers.add(newPeer);
    }

    @Scheduled(fixedRate =  30 * 60 * 1000)
    public void purge(){
        long currentTime = Calendar.getInstance().getTime().getTime();
        for(int peerIndex = 0; peerIndex < discoveredPeers.size(); peerIndex++){
            Peer peer = discoveredPeers.get(peerIndex);
            if(peer.getDate().getTime() < (currentTime - 15 * 60 * 1000)){
                discoveredPeers.remove(peerIndex);
            }
        }
        logger.info("Peer purged");
    }


    private boolean exist(String ip){
        DiscoverExistsLambda existsLambda = string -> discoveredPeers.stream().anyMatch(peer -> peer.getIp().equals(string));
        return existsLambda.exists(ip);
    }

    private void update(Peer peerToUpdate){
        DiscoverUpdateLambda updateLambda = peerNoUpdated -> discoveredPeers.stream().filter(peer -> peer.getIp().equals(peerNoUpdated.getIp())).forEach(peer -> {
            peer.setDate(peerNoUpdated.getDate());
            peer.setName(peerNoUpdated.getName());
        });
        updateLambda.update(peerToUpdate);
    }

    @Override
    public String toString() {
        StringBuilder peers = new StringBuilder();
        for (Peer peer :discoveredPeers) {
            peers.append(peer.toString());
            peers.append("</br>");
        }
        return peers.toString();
    }

}
