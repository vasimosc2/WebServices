package org.acme;
import java.util.List;

import org.acme.models.CustInt;
import org.acme.models.Customer;
import org.acme.services.CustomerService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;

@Path("/customer")
public class CustomerResources {
    CustomerService service = new CustomerService();
    
    public record UserAccountId(User user, String accountId) {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> customer() {
        return service.getCustomers();
    }
    



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(CustInt custInt) throws Exception {
        String internalId = service.sendRegisterEvent(custInt);
        return Response.ok().entity(internalId).build();
        
    }

}
