package microservicios2.micro2.controller;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Peer {
    private String ip;
    private Date date;
    private String name;

    public String getIp() {
        return ip;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

}
