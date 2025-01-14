package org.acme;
import java.util.List;
import org.acme.models.Merchant;
import org.acme.services.MerchantService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/merchant")
public class MerchantResources{
    MerchantService service = new MerchantService();
    public record UserAccountId(User user, String accountId) {}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> customer() {
        return service.getMerchants();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomerBank(UserAccountId userAccountId){
        User user = userAccountId.user;
        String accountId = userAccountId.accountId;
        service.setMerchant(user,accountId);
    }
}
