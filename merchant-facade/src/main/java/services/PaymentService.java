package services;

import com.google.gson.Gson;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.PaymentManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static utils.EventTypes.*;

public class PaymentService implements EventReceiver {

    private CompletableFuture<Boolean> requestPaymentResult;
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
            case PAYMENT_REQUEST_SUCCESS:
                System.out.println("I got PaymentSuccessful");

                correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                System.out.println("The correlationID is: ");
                System.out.println(correlationId);
                correlations.get(correlationId).complete(true);
                break;
            case PAYMENT_REQUEST_FAILED:
                requestPaymentResult.complete(false);
                break;
            case GET_CUSTOMER_ID_BY_TOKEN_ID_REQUEST_FAILED:
                System.out.println("I am at the MerchantFacade/PaymentService and The Token was not valid");
                correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                System.out.println("On the failure Token" + correlationId);
                correlations.get(correlationId).complete(false);
                break;
            default:
                System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }


    public boolean sendPaymentEvent(BankPay bankpay) throws Exception{
        String correlationId = UUID.randomUUID().toString();
        correlations.put(correlationId, new CompletableFuture<>());

        String eventType = PAYMENT_REQUEST;
        Object[] arguments = new Object[]{bankpay, correlationId};
        Event event = new Event(eventType, arguments);
        eventSender.sendEvent(event);

        return correlations.get(correlationId).join();

    }

    public List<PaymentManager> getAllPayments() throws Exception {
        String eventType = "RequestAllPayments";
        Object[] arguments = new Object[]{};
        Event event = new Event(eventType, arguments);
        ManagerPayments = new CompletableFuture<>();
        eventSender.sendEvent(event);

        return ManagerPayments.join();
    }


}