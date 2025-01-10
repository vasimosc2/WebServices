package dtu.example;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public class HelloService {
    public String hello(){
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target("http://localhost:8081/");
        return target.path("hello").request().get(String.class);
    }
}
