package dtu.example.services;

import dtu.example.models.Person;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class PersonService {
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8081/");
    
    public Person getPerson() {
        
        return target.path("person").request().get(Person.class);
    }

    public void setPerson(String string, String string2) {
        Person person = new Person(string, string2);
        target.path("person").request().put(Entity.entity(person, MediaType.APPLICATION_JSON));
    }
}


