package microservicios2.micro2.discover;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastingClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int expectedServerCount;
    private byte[] buf;
    private Logger logger = LoggerFactory.getLogger(BroadcastingClient.class);

    public BroadcastingClient(int expectedServerCount) throws Exception {
        this.expectedServerCount = expectedServerCount;
        this.address = InetAddress.getByName("255.255.255.255");
    }

    public int discoverServers(String msg) throws IOException {
        initializeSocketForBroadcasting();
        copyMessageOnBuffer(msg);
        broadcastPacket(address);
        return receivePackets();
    }

    private void initializeSocketForBroadcasting() throws SocketException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }

    private void copyMessageOnBuffer(String msg) {
        buf = msg.getBytes();
    }

    private void broadcastPacket(InetAddress address) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
    }

    private int receivePackets() throws IOException {
        int serversDiscovered = 0;
        while (serversDiscovered != expectedServerCount) {
            receivePacket();
            serversDiscovered++;
        }
        return serversDiscovered;
    }

    void receivePacket() throws IOException {
        buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String (packet.getData(), 0, packet.getLength());
        InetAddress address = packet.getAddress();
        logger.info("Received "+received+".");
        logger.info("Address "+address.toString()+".");
    }

    public void close() {
        socket.close();
    }
}