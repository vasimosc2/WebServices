package org.acme.resources;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.acme.models.Merchant;
import org.acme.services.MerchantService;
import jakarta.ws.rs.core.MediaType;

@Path("/merchants")
public class MerchantResources{

    @Inject
    MerchantService service;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> getMerchants() {
        return service.getMerchants();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addMerchant(Merchant merchant){
        return service.registerMerchant(merchant);
    }
}
