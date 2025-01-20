package services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import jakarta.ws.rs.core.Response;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.MerchInt;
import models.Merchant;

public class MerchantService implements EventReceiver  {
    private List<Merchant> merchants = new ArrayList<>();

    private CompletableFuture<String> registerResult;
    private CompletableFuture<Merchant> GetMerchantResult;
    private CompletableFuture<Response> retireMerchant;


    private EventSender eventSender;
    private final Gson gson = new Gson();

    public MerchantService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public List<Merchant> getMerchants() {
        return merchants;
    }


    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case "RegisterMerchantSuccessful":
                System.out.println("I got a merchant success");
                String MerchnatId = (String) eventIn.getArguments()[0];
                registerResult.complete(MerchnatId); 
                break;

            case "RegisterMerchantFailed":
                registerResult.complete(null);
                break;

            case "GetMerchantSuccessfull":
                System.out.println("I got a GetMerchantSucessfull");
                Merchant Merchnat = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                GetMerchantResult.complete(Merchnat);
                break;
            case "GetMerchantFailed":
                GetMerchantResult.complete(null);
                break;
            case "RetiremerchantByCprSuccessful":
                System.out.println("I got a RetiremerchantByCprSuccessful");
                boolean removed = merchants.removeIf(c -> c.getCprNumber().equals((String) eventIn.getArguments()[0]));

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






    public String sendRegisterEvent(MerchInt merchInt) throws Exception{
        String eventType = "RegisterMerchant";
        Object[] arguments = new Object[]{merchInt};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        String MerchnatId = registerResult.join();
        
        Merchant newMerchnat = merchInt.getMerchant();
        if(MerchnatId!=null){
            newMerchnat.setId(MerchnatId);
            merchants.add(newMerchnat);
            return MerchnatId;
        }
        return null;
    }



    public Merchant GetMerchantByCpr(String cprNumber) throws Exception {
        String eventType = "GetMerchant";
        Object[] arguments = new Object[]{cprNumber};
        Event event = new Event(eventType,arguments);
        GetMerchantResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        return GetMerchantResult.join();
    }



    public Response retireAccount(String cprNumber) throws Exception {
        String eventType = "Retiremerchant";
        Object[] arguments = new Object[]{cprNumber};
        Event event = new Event(eventType,arguments);
        retireMerchant = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return retireMerchant.join();
    }
}