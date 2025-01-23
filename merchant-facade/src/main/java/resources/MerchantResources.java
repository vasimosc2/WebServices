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

import messaging.rabbitmq.payment.PaymentFactory;
import models.BankPay;
import models.Merchant;
import services.MerchantService;
import services.PaymentService;


@Path("/merchants")
public class MerchantResources{
    MerchantService merchantService = MerchantFactory.getService();

    PaymentService paymentService = PaymentFactory.getService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> merchants() {
        return merchantService.getMerchantIds();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(Merchant merchant) throws Exception {
        String merchantId = merchantService.sendRegisterEvent(merchant);
        return Response.ok().entity(merchantId).build(); // this returns to the test and gives a 200 status with the item
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{merchantId}")
    public Merchant getMerchantByMerchantId(@PathParam("merchantId") String merchantId) throws Exception {
        return merchantService.getMerchantByMerchantId(merchantId);
    }

    @DELETE
    @Path("/{merchantId}")
    public Response retireAccount(@PathParam("merchantId") String merchantId) throws Exception {
        System.out.println(merchantId);
       return merchantService.retireAccount(merchantId);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/payments")
    public Response setBankPayment(BankPay bankpay) throws Exception {
        boolean successful = paymentService.sendPaymentEvent(bankpay);
        return Response.ok().entity(successful).build();
    }



}
