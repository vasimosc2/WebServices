package org.acme;

import java.util.List;

import org.acme.models.Merchant;
import org.acme.services.MerchantService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/merchant")
public class MercuhantResource {
    MerchantService service = new MerchantService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> Merchant() {
        return service.getMerchants();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setMerchant(Merchant merchant){
        service.setMerchant(merchant);
    }
}
