package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Calendar;


@Component
public class DiscoverCache implements Cache{

    @Value("${inactivityLimit}")
    int inactivityLimit;

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

    @Scheduled(fixedRateString =  "${purgueInterval}")
    public void purge(){

        long currentTime = Calendar.getInstance().getTime().getTime();
        for(int peerIndex = 0; peerIndex < discoveredPeers.size(); peerIndex++){
            Peer peer = discoveredPeers.get(peerIndex);
            if(peer.getDate().getTime() < (currentTime - inactivityLimit)){
                System.out.println("entra");
                discoveredPeers.remove(peerIndex);
            }

            System.out.println(peer.getDate().getTime());
        }
        System.out.println("purgado");
    }

    private boolean exist(String ip){
        for (Peer peer :discoveredPeers) {
            if(peer.getIp().equals(ip)){
                return true;
            }
        }
        return false;
    }

    private void update(Peer peerToUpdate){
        for (Peer peer :discoveredPeers) {
            if (peer.getIp().equals(peerToUpdate.getIp())){
                peer.setDate(peerToUpdate.getDate());
                peer.setName(peerToUpdate.getName());
            }
        }
    }

}
