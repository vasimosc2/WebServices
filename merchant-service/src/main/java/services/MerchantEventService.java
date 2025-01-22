package services;
import com.google.gson.Gson;

import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.Merchant;
import services.interfaces.IMerchantService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.EventTypes.*;

public class MerchantEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(MerchantEventService.class.getName());

    private final EventSender eventSender;
    
    private final IMerchantService service;
    private final Gson gson = new Gson();

    public MerchantEventService(EventSender eventSender, MerchantService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case REGISTER_MERCHANT_REQUEST:
                try {
                    System.out.println("Hello from RegisterMerchant");
                    Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                    System.out.println(merchant.getFirstName());
                    String merchantId = service.register(merchant);
                    System.out.println(merchantId);
                    Event eventOut = new Event(REGISTER_MERCHANT_REQUEST_SUCCESS, new Object[]{merchantId});

                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(REGISTER_MERCHANT_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case GET_MERCHANT_REQUEST:
                try {
                    System.out.println("Hello from GetMerchant");
                    String merchantId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(String.format("I am at MerchnatEventService: %s", merchantId));

                    Merchant Merchant = service.getAccount(merchantId);
                    Event eventOut = new Event(GET_MERCHANT_REQUEST_SUCCESS, new Object[]{Merchant});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(GET_MERCHANT_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetMerchantByMerchantId":
                try {
                    System.out.println("Hello from GetMerchantByMerchantId");
                    String merchantId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(String.format("I am at MerchnatEventService: %s", merchantId));

                    Merchant Merchant = service.getMerchantById(merchantId);
                    Event eventOut = new Event("SuccessfullGetMerchantByMerchantId", new Object[]{Merchant});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetMerchantFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case RETIRE_MERCHANT_REQUEST:
                try {
                    System.out.println("Hello from Retiremerchant");
                    String merchantId = (String) eventIn.getArguments()[0];
                    service.retireAccount(merchantId);

                    Event eventOut = new Event(RETIRE_MERCHANT_REQUEST_SUCCESS, new Object[]{merchantId});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(RETIRE_MERCHANT_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
