package services;

import com.google.gson.Gson;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Token;
import models.TokenInt;

import java.util.concurrent.CompletableFuture;

public class PaymentService implements EventReceiver {

    private CompletableFuture<Boolean> requestPaymentResult;
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
            case "PaymentSuccessfull":
                System.out.println("I got a successfull RequestPayment request");
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

}