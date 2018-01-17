package microservicios2.micro2.controller;

import microservicios2.micro2.discover.Discover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @Autowired
    private Discover discover;

    @RequestMapping(value = "/controller/{ip}/{time}/{name}",method = RequestMethod.GET)
    public void getMessage(@PathVariable("ip") String ip,
                             @PathVariable("time") String time,
                             @PathVariable("name") String name){

        //Cambiar por el bean
        Peer peer = new Peer();
        peer.setIp(ip);
        //peer.setDate();
        peer.setName(name);

        discover.insert(peer);
    }
}
