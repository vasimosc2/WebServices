
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
import messaging.rabbitmq.reporting.ReportingFactory;
import messaging.rabbitmq.token.TokenFactory;
import models.Customer;
import models.PaymentCustomer;
import models.Token;
import models.TokenInt;
import services.CustomerService;
import services.ReportingService;
import services.TokenService;
@Path("/customers")
public class CustomerResources {
    private final CustomerService customerService = CustomerFactory.getService();
    private final TokenService tokenService = TokenFactory.getService();
    private final ReportingService reportingService = ReportingFactory.getService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerCustomer(Customer customer) throws Exception {
        String customerId = customerService.sendRegisterEvent(customer);
        return Response.ok().entity(customerId).build();
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
    @Path("/{customerId}/tokens")
    public Response requestTokens(@PathParam("customerId") String customerId, int tokenAmount) throws Exception {
        TokenInt tokenInt = new TokenInt();
        tokenInt.setCustomerId(customerId);
        tokenInt.setAmount(tokenAmount);
        boolean successful = tokenService.sendGenerateTokensEvent(tokenInt);
        if (successful) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Token request failed").build();
        }
    }

    @GET
    @Path("/{customerId}/tokens")
    @Produces(MediaType.APPLICATION_JSON)
    public Token getToken(@PathParam("customerId") String customerId) throws Exception {
        System.out.println("I am at get one TOken");
        return tokenService.sendGetOneTokenEvent(customerId);
    }

    @GET
    @Path("/{customerId}/reports")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PaymentCustomer> getReport(@PathParam("customerId") String customerId) throws Exception {
        return reportingService.sendRetrieveCustomerReportEvent(customerId);
    }




}
