package org.acme;

import java.util.List;

import org.acme.models.Customer;
import org.acme.services.CustomerService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/customer")
public class CustomerResources {
    CustomerService service = new CustomerService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> customer() {
        return service.getCustomers();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomer(Customer customer){
        service.setCustomer(customer);
    }
}
