package microservicios2.micro2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @RequestMapping("/controller")
    public String prueba(){
        return "Hola mundo";
    }
}
