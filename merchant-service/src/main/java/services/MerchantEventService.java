/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package services;
import com.google.gson.Gson;
import exceptions.account.AccountNotFoundException;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Merchant;
import services.interfaces.IMerchantService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.EventTypes.*;

public class MerchantEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(MerchantEventService.class.getName());

    private final EventSender eventSender;
    private String correlationId;

    private final IMerchantService service;
    private final Gson gson = new Gson();

    public MerchantEventService(EventSender eventSender, MerchantService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case REGISTER_MERCHANT_REQUESTED:
                try {
                    System.out.println("Hello from REGISTER_MERCHANT_REQUESTED");
                    Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                    System.out.println(merchant.getFirstName());
                    String merchantId = service.register(merchant);
                    System.out.println(merchantId);
                    Event eventOut = new Event(MERCHANT_REGISTERED, new Object[]{merchantId});

                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(REGISTER_MERCHANT_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case GET_MERCHANT_REQUESTED:
                try {
                    System.out.println("Hello from GET_MERCHANT_REQUESTED");
                    String merchantId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(String.format("I am at MerchnatEventService: %s", merchantId));

                    Merchant Merchant = service.getMerchantById(merchantId);
                    Event eventOut = new Event(MERCHANT_RETRIEVED, new Object[]{Merchant});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(MERCHANT_NOT_RETRIEVED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;

            case PAYMENT_REQUESTED:
                try {
                    System.out.println("Hello from PAYMENT_REQUESTED at MerchantService");

                    BankPay bankpay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);
                    correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), String.class);
                    System.out.println(String.format("I am at Merchant Service at Paymentrequest and the id of the merchant is : %s", bankpay.getMerchantId()));

                    Merchant Merchant = service.getMerchantById(bankpay.getMerchantId());
                    Event eventOut = new Event(MERCHANT_BY_MERCHANT_ID_RETRIEVED, new Object[]{Merchant,correlationId});
                    eventSender.sendEvent(eventOut);

                } catch (AccountNotFoundException e) {
                    System.out.println("Merchant account not found: " + e.getMessage());
                    Event eventOut = new Event(GET_MERCHANT_BY_MERCHANT_ID_FAILED, new Object[]{correlationId});
                    eventSender.sendEvent(eventOut);

                } catch (Exception e) {
                    System.err.println("An unexpected error occurred: " + e.getMessage());
                    Event eventOut = new Event(GET_MERCHANT_BY_MERCHANT_ID_FAILED, new Object[]{correlationId != null ? correlationId : "Unknown correlationId"});
                    eventSender.sendEvent(eventOut);
                }
                break;

            case RETIRE_MERCHANT_REQUESTED:
                try {
                    System.out.println("Hello from RETIRE_MERCHANT_REQUEST");
                    String merchantId = (String) eventIn.getArguments()[0];
                    service.retireAccount(merchantId);

                    Event eventOut = new Event(MERCHANT_RETIRED, new Object[]{merchantId});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(RETIRE_MERCHANT_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
