package microservicios2.micro2.hilo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


@Component
public class Hilo{
    @Autowired
    private Thread thread;

    @PostConstruct
    public void run(){
        thread.start();
    }

}