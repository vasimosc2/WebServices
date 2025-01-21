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
import messaging.rabbitmq.merchant.MerchantFactory;

import models.MerchInt;
import models.Merchant;
import services.MerchantService;




@Path("/merchant")
public class MerchantResources{
    MerchantService service = MerchantFactory.getService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> merchants() {
        return service.getMerchants();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cprNumber}")
    public Merchant getCprNumber(@PathParam("cprNumber") String cprNumber) throws Exception {

        return service.GetMerchantByCpr(cprNumber);
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
    public Response register(MerchInt merchInt) throws Exception {
        String merchantId = service.sendRegisterEvent(merchInt);
        return Response.ok().entity(merchantId).build(); // this returns to the test and gives a 200 status with the item
    }

}
