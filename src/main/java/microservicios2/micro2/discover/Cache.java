package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;

public interface Cache {
    void insertPeer(Peer peer);
    void purge();
}
