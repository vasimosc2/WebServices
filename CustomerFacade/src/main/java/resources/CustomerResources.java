
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

    private final CustomerService customerService = CustomerFactory.getService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllCustomers() {
        return customerService.getCustomers();
    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomerByCustomerId(@PathParam("customerId") String customerId) throws Exception {
        return customerService.getCustomerByCustomerId(customerId);
    }

    @DELETE
    @Path("/{customerId}")
    public Response retireAccount(@PathParam("customerId") String customerId) throws Exception {
        System.out.println("Retiring account for Customer Id: " + customerId);
        return customerService.retireAccount(customerId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerCustomer(Customer customer) throws Exception {
        String customerId = customerService.sendRegisterEvent(customer);
        return Response.ok().entity(customerId).build();
    }

    // Expose the TokenResources as a sub-resource
    @Path("/tokens")
    public TokenResources getTokenResources() {
        return new TokenResources();
    }

    public static class TokenResources {

        private final TokenService tokenService = TokenFactory.getService();

        @POST
        @Path("/request")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response requestTokens(TokenInt tokenInt) throws Exception {
            System.out.println("I reached DTUPay");
            boolean successful = tokenService.sendRequestTokensEvent(tokenInt);
            if (successful) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Token request failed").build();
            }
        }

        @POST
        @Path("/getToken")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Token getToken(String customerId) throws Exception {
            System.out.println("I am at getTOken");
            return tokenService.sendGetTokenRequest(customerId);
        }
    }
}
