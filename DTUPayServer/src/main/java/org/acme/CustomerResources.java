package org.acme;
import java.util.List;

import jakarta.ws.rs.*;
import org.acme.models.Customer;
import org.acme.services.CustomerService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerResources {
    CustomerService service = new CustomerService();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addCustomer(Customer customer){
        return service.setCustomer(customer);
    }
}
