package microservicios2.micro2.hilo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class Hilo{
    @Autowired
    Thread thread;

    @PostConstruct
    public void run(){
        thread.start();
    }

    @PreDestroy
    public void stop(){
        thread.destroy();
    }

}