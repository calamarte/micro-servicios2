package microservicios2.micro2.controller;

import microservicios2.micro2.discover.Discover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class ServiceController {

    @Autowired
    private Discover discover;

    @RequestMapping(value = "/controller/{ip}/{time}/{name}",method = RequestMethod.POST)
    public void getMessage(@PathVariable("ip") String ip,
                             @PathVariable("time") String time,
                             @PathVariable("name") String name){

        Date date = new Date();
        date.setTime(Long.parseLong(time));

        Peer peer = new Peer();
        peer.setIp(ip);
        peer.setDate(date);
        peer.setName(name);

        discover.insert(peer);
    }


    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() throws Exception {
        discover.sendBroadcast();
    }
}
