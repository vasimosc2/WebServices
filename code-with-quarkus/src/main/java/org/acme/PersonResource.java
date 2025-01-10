package org.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/person")
public class PersonResource {
    PersonService service = new PersonService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person Person() {
        return service.getPerson();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setPerson(Person person){
        service.setPerson(person);
    }
}
