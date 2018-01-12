package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class BroadcastingEchoServer extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[1024];
    private String myIp = InetAddress.getLocalHost().toString().split("/")[1];
    private String myHostName = InetAddress.getLocalHost().getHostName();


    public BroadcastingEchoServer() throws IOException {
        socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(4445));
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String received = new String(packet.getData(), 0, packet.getLength());

                String s = received.substring(0,3);
                System.out.println("Mi ip: "+myIp);
                System.out.println(received+" ip: "+address.toString());

//                try {
//                    BroadcastingClient client = new BroadcastingClient(1);
//                    client.discoverServers(myIp,address);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (s.equals("end")) {
                    running = false;
                    continue;
                }
                String mistDatos = myIp+" ruta "+myHostName;
                buf = mistDatos.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}