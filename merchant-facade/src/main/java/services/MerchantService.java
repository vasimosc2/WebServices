/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */


package services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;
import static utils.EventTypes.*;
import static utils.EventTypes.MERCHANT_REGISTERED;

import com.google.gson.Gson;
import jakarta.ws.rs.core.Response;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.Merchant;

public class MerchantService implements EventReceiver  {
    private CompletableFuture<String> registerResult;
    private CompletableFuture<Merchant> GetMerchantResult;
    private CompletableFuture<Response> retireMerchant;



    private EventSender eventSender;
    private final Gson gson = new Gson();

    public MerchantService(EventSender eventSender) {
        this.eventSender = eventSender;
    }




    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case MERCHANT_REGISTERED:
                System.out.println("I got a MERCHANT_REGISTERED");
                String merchantId = (String) eventIn.getArguments()[0];
                registerResult.complete(merchantId);
                break;

            case REGISTER_MERCHANT_FAILED:
                registerResult.complete(null);
                break;

            case MERCHANT_RETRIEVED:
                System.out.println("I got a MERCHANT_RETRIEVED");
                Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                GetMerchantResult.complete(merchant);
                break;

            case MERCHANT_NOT_RETRIEVED:
                GetMerchantResult.complete(null);
                break;

            case MERCHANT_RETIRED:
                System.out.println("I got a MERCHANT_RETIRED");
                boolean removed = !isNull(eventIn.getArguments()[0]);

                if (!removed) {
                    System.out.println("No Merchnat found with CPR: " + eventIn.getArguments()[0]);
                }

                retireMerchant.complete(Response.status(200).entity("Delete successful").build());
                break;

            case RETIRE_MERCHANT_FAILED:
                retireMerchant.complete(Response.status(404).entity("Delete successful").build());
                break;
            default:
                System.out.println("Ignored event in Rest with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }




    public String sendRegisterEvent(Merchant merchant) throws Exception{
        String eventType = REGISTER_MERCHANT_REQUESTED;
        Object[] arguments = new Object[]{merchant};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        String merchantId = registerResult.join();

        if(merchantId!=null){
            return merchantId;
        }
        return null;
    }

    public Merchant getMerchantByMerchantId(String merchantId) throws Exception {
        String eventType = GET_MERCHANT_REQUESTED;
        Object[] arguments = new Object[]{merchantId};
        Event event = new Event(eventType,arguments);
        GetMerchantResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        return GetMerchantResult.join();
    }



    public Response retireAccount(String merchantId) throws Exception {
        String eventType = RETIRE_MERCHANT_REQUESTED;
        Object[] arguments = new Object[]{merchantId};
        Event event = new Event(eventType,arguments);
        retireMerchant = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return retireMerchant.join();
    }

}