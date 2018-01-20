package microservicios2.micro2.hilo;


import microservicios2.micro2.controller.Peer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

@Component
public class BroadcastingEchoServer extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[1024];

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
                int port = packet.getPort();
                String received = new String(packet.getData(), 0, packet.getLength());

                String data = received.replaceAll("\00","");
                String[] dataArray = data.split(" ");

                if(dataArray[1].startsWith("http://"))sentHttp(dataArray[1]);

            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }

    @Override
    public void destroy() {
        running = false;
    }

    private void sentHttp(String url) {
        try {
            String ip = getFirstNonLoopbackAddress();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date now = Calendar.getInstance().getTime();
            String time = dateFormat.format(now);
            String name = InetAddress.getLocalHost().getHostName();

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation(url + "/"+ip+"/"+time+"/"+name, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFirstNonLoopbackAddress() throws SocketException {
        Enumeration enumerationInterfaces = NetworkInterface.getNetworkInterfaces();
        while (enumerationInterfaces.hasMoreElements()) {
            NetworkInterface anInterface = (NetworkInterface) enumerationInterfaces.nextElement();
            for (Enumeration enumerationAddresses = anInterface.getInetAddresses(); enumerationAddresses.hasMoreElements();) {
                InetAddress addr = (InetAddress) enumerationAddresses.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        return addr.toString();
                    }

                }
            }
        }
        return null;
    }
}