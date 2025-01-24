/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */


package services;

import com.google.gson.Gson;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Customer;
import models.PaymentManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static utils.EventTypes.*;

public class PaymentService implements EventReceiver {

    private CompletableFuture<List<PaymentManager>> ManagerPayments;
    private Map<String,CompletableFuture<Boolean>> correlations = new ConcurrentHashMap<>();

    private String correlationId;



    private EventSender eventSender;
    private final Gson gson = new Gson();

    public PaymentService(EventSender eventSender)  {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case PAYMENT_HAS_SUCCEEDED:
                System.out.println("I got PAYMENT_HAS_SUCCEEDED");

                correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                System.out.println("The correlationID is: ");
                System.out.println(correlationId);
                correlations.get(correlationId).complete(true);
                break;
            case PAYMENT_HAS_FAILED:
                System.out.println("I got failed Payment");
                correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                System.out.println("The correlationId of the Failed Payment is :" + correlationId);
                correlations.get(correlationId).complete(false);
                break;
            case GET_CUSTOMER_ID_BY_TOKEN_ID_REQUEST_FAILED:
                System.out.println("I am at the MerchantFacade/PaymentService and The Token was not valid");
                correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                System.out.println("On the failure Token" + correlationId);
                correlations.get(correlationId).complete(false);
                break;
            case GET_MERCHANT_BY_MERCHANT_ID_FAILED:
                System.out.println("I am at the MerchantFacade/PaymentService and The Merchant did not exist at DTU pay");
                correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                System.out.println("On the failure Merchant Service" + correlationId);
                correlations.get(correlationId).complete(false);
            default:
                System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }


    public boolean sendPaymentEvent(BankPay bankpay) throws Exception{
        String correlationId = UUID.randomUUID().toString();
        correlations.put(correlationId, new CompletableFuture<>());

        String eventType = PAYMENT_REQUESTED;
        Object[] arguments = new Object[]{bankpay, correlationId};
        Event event = new Event(eventType, arguments);
        eventSender.sendEvent(event);

        return correlations.get(correlationId).join();

    }


}