package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;

public interface DicoverInterface {
    void sendBroadcast() throws Exception;
    void insert(Peer peer);
}
