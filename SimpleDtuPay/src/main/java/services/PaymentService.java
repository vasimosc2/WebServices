package services;

import com.google.gson.Gson;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.PaymentManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PaymentService implements EventReceiver {

    private CompletableFuture<Boolean> requestPaymentResult;
    private CompletableFuture<List<PaymentManager>> ManagerPayments;
//    private CompletableFuture<Token> getTokenResult;
//    private CompletableFuture<Boolean> retireCustomerTokensResult;


    private EventSender eventSender;
    private final Gson gson = new Gson();

    public PaymentService(EventSender eventSender)  {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case "PaymentSuccessful":
                System.out.println("I got PaymentSuccessful");
                requestPaymentResult.complete(true);
                break;
            case "RequestPaymentFailed":
                requestPaymentResult.complete(false);
                break;
            default:
                System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }


    public boolean sendPaymentEvent(BankPay bankpay) throws Exception{
        String eventType = "RequestPayment";
        Object[] arguments = new Object[]{bankpay};
        Event event = new Event(eventType, arguments);
        requestPaymentResult = new CompletableFuture<>();
        eventSender.sendEvent(event);

        return requestPaymentResult.join();

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