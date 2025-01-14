package org.acme;
import java.util.List;

import jakarta.ws.rs.*;
import org.acme.models.Merchant;
import org.acme.services.MerchantService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.core.MediaType;

@Path("/merchants")
public class MerchantResources{
    MerchantService service = new MerchantService();
    public record UserAccountId(User user, String accountId) {}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> getMerchants() {
        return service.getMerchants();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addMerchant(Merchant merchant){
        return service.setMerchant(merchant);
    }
}
