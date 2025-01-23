package services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static utils.EventTypes.*;
import com.google.gson.Gson;
import jakarta.ws.rs.core.Response;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Merchant;

public class MerchantService implements EventReceiver  {
    private List<String> merchantIds = new ArrayList<>();

    private CompletableFuture<String> registerResult;
    private CompletableFuture<Merchant> GetMerchantResult;
    private CompletableFuture<Response> retireMerchant;


    private EventSender eventSender;
    private final Gson gson = new Gson();

    public MerchantService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public List<String> getMerchantIds() {
        return merchantIds;
    }


    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case "RegisterMerchantSuccessful":
                System.out.println("I got a RegisterMerchantSuccessful");
                String merchantId = (String) eventIn.getArguments()[0];
                registerResult.complete(merchantId);
                break;

            case "RegisterMerchantFailed":
                registerResult.complete(null);
                break;

            case "GetMerchantSuccessfull":
                System.out.println("I got a GetMerchantSucessfull");
                Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                GetMerchantResult.complete(merchant);
                break;

            case "GetMerchantFailed":
                GetMerchantResult.complete(null);
                break;

            case "RetiremerchantByCprSuccessful":
                System.out.println("I got a RetiremerchantByCprSuccessful");
                boolean removed = merchantIds.removeIf(c -> c.equals((String) eventIn.getArguments()[0]));

                if (!removed) {
                    System.out.println("No Merchnat found with CPR: " + eventIn.getArguments()[0]);
                }

                retireMerchant.complete(Response.status(200).entity("Delete successful").build());
                break;

            case "RetiremerchantByCprFailed":
                retireMerchant.complete(Response.status(404).entity("Delete successful").build());
                break;

            default:
                System.out.println("Ignored event in Rest with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }






    public String sendRegisterEvent(Merchant merchant) throws Exception{
        String eventType = REGISTER_MERCHANT_REQUEST;
        Object[] arguments = new Object[]{merchant};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        String merchantId = registerResult.join();

        if(merchantId!=null){
            merchantIds.add(merchantId);
            return merchantId;
        }
        return null;
    }

    public Merchant getMerchantByMerchantId(String merchantId) throws Exception {
        String eventType = GET_MERCHANT_REQUEST;
        Object[] arguments = new Object[]{merchantId};
        Event event = new Event(eventType,arguments);
        GetMerchantResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        return GetMerchantResult.join();
    }



    public Response retireAccount(String merchantId) throws Exception {
        String eventType = RETIRE_MERCHANT_REQUEST;
        Object[] arguments = new Object[]{merchantId};
        Event event = new Event(eventType,arguments);
        retireMerchant = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return retireMerchant.join();
    }

    public boolean sendPaymentEvent(BankPay bankpay) throws Exception{
        String eventType = PAYMENT_REQUEST;
        Object[] arguments = new Object[]{bankpay};
        Event event = new Event(eventType, arguments);
        requestPaymentResult = new CompletableFuture<>();
        eventSender.sendEvent(event);

        return requestPaymentResult.join();

    }
}