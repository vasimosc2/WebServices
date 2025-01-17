package org.acme.resources;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.acme.models.Customer;
import org.acme.services.CustomerService;
import jakarta.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerResources {
    @Inject
    CustomerService service;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addCustomer(Customer customer){
        return service.registerCustomer(customer);
    }
}
