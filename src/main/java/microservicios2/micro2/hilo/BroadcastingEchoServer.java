package microservicios2.micro2.hilo;

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
            System.out.println("hola amigo");
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String received = new String(packet.getData(), 0, packet.getLength());

                String data = received.replaceAll("\00","");
                String[] dataArray = data.split(" ");

                String ip = dataArray[0];
                String url = dataArray[1];
                String hostname = dataArray[2];
                String date = dataArray[3];

                for (String d:dataArray) {
                    System.out.println(d);
                }

//
//                if (s.equals("end")) {
//                    running = false;
//                    continue;
//                }
//                String mistDatos = myIp+" ruta "+myHostName;
//                buf = mistDatos.getBytes();
//                packet = new DatagramPacket(buf, buf.length, address, port);
//                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}