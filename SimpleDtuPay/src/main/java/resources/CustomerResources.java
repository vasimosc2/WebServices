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
import messaging.rabbitmq.token.TokenFactory;
import models.Customer;
import models.Token;
import models.TokenInt;
import services.CustomerService;
import services.TokenService;

@Path("/customers")
public class CustomerResources {
    CustomerService customerService = CustomerFactory.getService();
    TokenService tokenService = TokenFactory.getService();
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> customer() {
        return customerService.getCustomers();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{cprNumber}")
    public Customer getCprNumber(@PathParam("cprNumber") String cprNumber) throws Exception {
        return customerService.getCustomerByCpr(cprNumber);
    }


    @DELETE
    @Path("/{cprNumber}")
    public Response retireAccount(@PathParam("cprNumber") String cprNumber) throws Exception {
        System.out.println(cprNumber);
       return customerService.retireAccount(cprNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Customer customer) throws Exception {
        String customerId = customerService.sendRegisterEvent(customer);
        return Response.ok().entity(customerId).build();
    }

    @Path("/tokens")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response requestTokens(TokenInt tokenInt) throws Exception {
        boolean successful = tokenService.sendRequestTokensEvent(tokenInt);
        if (successful) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Token request failed").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Token getToken(String customerId) throws Exception{
        return tokenService.sendGetTokenRequest(customerId);
    }

}
