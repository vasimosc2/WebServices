package services;
import com.google.gson.Gson;


import interfaces.rabbitmq.payment.PaymentSender;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Customer;
import services.interfaces.IPaymentService;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.Response;

public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(PaymentEventService.class.getName());

    private final EventSender eventSender;
    
    private final IPaymentService service;
    private final Gson gson = new Gson();

    public PaymentEventService(EventSender eventSender, PaymentService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case "RequestPayment":
                try {
                    System.out.println("Hello from RequestPayment");
                    BankPay bankPay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);
                    
                    System.out.println(bankPay.money());
                    System.out.println(bankPay.tokenId());
                    System.out.println(bankPay.merchantId());

                    Response response = service.requestPayment(bankPay);

                    Event eventOut = new Event("RequestPaymentSuccessfull", new Object[]{response.getStatus()});
                    System.out.println("Ready to send back");
                    eventSender.sendEvent(eventOut);
                    
                } catch (Exception e) {
                    Event eventOut = new Event("RequestPaymentFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }

    public Customer sendRequestCustomerByTokenIdEvent(String tokenId) throws Exception{
        String eventType = "RequestCustomerByTokenEvent";
        Object[] arguments = new Object[]{tokenId};
        Event event = new Event(eventType, arguments);
        CompletableFuture<Customer> requestCustomerByTokenIdResult = new CompletableFuture<>();
        eventSender.sendEvent(event);

        return requestCustomerByTokenIdResult.join();
    }

}
