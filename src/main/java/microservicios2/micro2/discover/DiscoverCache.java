package microservicios2.micro2.discover;

import com.sun.javafx.util.Logging;
import microservicios2.micro2.controller.Peer;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

@Component
public class DiscoverCache implements Cache{
    private ArrayList<Peer> discoveredPeers = new ArrayList<>();

    public void insertPeer(Peer newPeer){
        System.out.println("--------------------------------------------------------");
        for (Peer peer : discoveredPeers) {
            System.out.println(peer.toString());
        }
        System.out.println("--------------------------------------------------------");
        if(exist(newPeer.getIp())){
            update(newPeer);
            return;
        }
        discoveredPeers.add(newPeer);
    }

//    @Scheduled(fixedRate =  30 * 60 * 1000)
    @Scheduled(fixedRate =  15000)
    public void purge(){
        long currentTime = Calendar.getInstance().getTime().getTime();
        for(int peerIndex = 0; peerIndex < discoveredPeers.size(); peerIndex++){
            Peer peer = discoveredPeers.get(peerIndex);
            if(peer.getDate().getTime() < (currentTime - 15000)){
                discoveredPeers.remove(peerIndex);
            }
            System.out.println(peer.getDate().getTime());
        }
        System.out.println("purgado");
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

}
