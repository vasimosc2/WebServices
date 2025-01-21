package resources;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import messaging.rabbitmq.customer.CustomerFactory;
import models.CustInt;
import models.Customer;
import services.CustomerService;

@Path("/customers")
public class CustomerResources {
    CustomerService service = CustomerFactory.getService();
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> customer() {
        return service.getCustomers();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cprNumber}")
    public Customer getCprNumber(@PathParam("cprNumber") String cprNumber) throws Exception {

        return service.getCustomerByCpr(cprNumber);
    }

    @DELETE
    @Path("/deleted/{cprNumber}")
    public Response retireAccount(@PathParam("cprNumber") String cprNumber) throws Exception {
        System.out.println(cprNumber);
       return service.retireAccount(cprNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Customer customer) throws Exception {
        String customerId = service.sendRegisterEvent(customer);
        return Response.ok().entity(customerId).build();
    }

}
