package com.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/person")
public class PersonResource {

    private static Person person = new Person("Susan", "USA");  // Sample person

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson() {
        return Response.ok(person).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person newPerson) {
        person.setName(newPerson.getName());
        person.setAddress(newPerson.getAddress());
        return Response.ok(person).build();
    }
}
