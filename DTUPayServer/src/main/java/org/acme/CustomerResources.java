package org.acme;
import java.util.List;
import org.acme.models.Customer;
import org.acme.services.CustomerService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerResources {
    CustomerService service = new CustomerService();
    public record UserAccountId(User user, String accountId) {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomerDtu(UserAccountId userAccountId){
        User user = userAccountId.user;
        String accountId = userAccountId.accountId;
        service.setCustomer(user,accountId);
    }
}
