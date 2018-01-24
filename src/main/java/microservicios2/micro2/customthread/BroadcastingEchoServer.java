package microservicios2.micro2.customthread;

import microservicios2.micro2.discover.DiscoverCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.*;
import java.util.Calendar;

import static microservicios2.micro2.utils.Utils.getFirstNonLoopbackAddress;

@Component
public class BroadcastingEchoServer extends Thread {

    protected DatagramSocket socket;
    protected boolean running;
    protected byte[] buf = new byte[1024];
    private Logger logger = LoggerFactory.getLogger(DiscoverCache.class);


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
                String received = new String(packet.getData(), 0, packet.getLength());

                String data = received.replaceAll("\00","");
                String[] dataArray = data.split(" ");

                if(dataArray.length >= 2 && dataArray[1].startsWith("http://")) sendHttp(dataArray[1]);

            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }


    private void sendHttp(String url) {
        try {
            String ip = getFirstNonLoopbackAddress();
            long now = Calendar.getInstance().getTime().getTime();
            String name = InetAddress.getLocalHost().getHostName();

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation(url + "/"+ip+"/"+now+"/"+name, null);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e){
            logger.error("Package refused");
        }

    }

}