package microservicios2.micro2.discover;

import microservicios2.micro2.controller.Peer;

public interface DiscoverUpdateLambda {
    void update(Peer peerToUpdate);
}
