package microservicios2.micro2.controller;

import microservicios2.micro2.discover.Discover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class ServiceController {

    @Autowired
    private Discover discover;

    @RequestMapping(value = "/controller/{ip}/{time}/{name}",method = RequestMethod.POST)
    public void getMessage(@PathVariable("ip") String ip,
                             @PathVariable("time") String time,
                             @PathVariable("name") String name){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        Peer peer = new Peer();
        peer.setIp(ip);
        try {
            peer.setDate(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        peer.setName(name);

        discover.insert(peer);
    }


    @Scheduled(fixedRate = 4000)
    public void reportCurrentTime() throws Exception {
        discover.sendBroadcast();
    }
}
