package microservicios2.micro2.controller;

import microservicios2.micro2.discover.Discover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @Autowired
    Discover discover;

    @RequestMapping("/controller")
    public String prueba(){
        return "Hola mundo";
    }
}
