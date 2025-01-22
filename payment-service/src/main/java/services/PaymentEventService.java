package services;
import com.google.gson.Gson;


import interfaces.rabbitmq.payment.PaymentSender;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Customer;
import models.Merchant;
import services.interfaces.IPaymentService;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.Response;

public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(PaymentEventService.class.getName());

    private final EventSender eventSender;
    
    private final IPaymentService service;

    private CompletableFuture<Customer> Future_customer;
    private CompletableFuture<Merchant> Future_merchant;


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

                    System.out.println(bankPay.getMoney());
                    System.out.println(bankPay.getMerchantId());
                    System.out.println(bankPay.getTokenId());

                    // Step 1: Send event to retrieve Customer
                    Event eventOut1 = new Event("GetCustomerIdFromTokenId", new Object[]{bankPay});
                    System.out.println("Sending to the Token Service .....");
                    eventSender.sendEvent(eventOut1);

                    // Step 2: Wait for the Customer to be retrieved
                    Customer retrievedCustomer = Future_customer.join(); // Block until customer is retrieved
                    System.out.println("I got a customer");
                    System.out.println(retrievedCustomer.getFirstName());

                    // Step 3: Send event to retrieve Merchant
                    Event eventOut2 = new Event("GetMerchantByMerchantId", new Object[]{bankPay.getMerchantId()});
                    System.out.println("Sending to the Merchant Service .....");
                    eventSender.sendEvent(eventOut2);

                    // Step 4: Wait for the Merchant to be retrieved
                    Merchant retrievedMerchant = Future_merchant.join(); // Block until merchant is retrieved
                    System.out.println("I got the merchant");
                    System.out.println(retrievedMerchant.getFirstName());

                    // Step 5: Send the PaymentSuccessful event
                    Event eventOut3 = new Event("PaymentSuccessful", new Object[]{}); // No objects required
                    eventSender.sendEvent(eventOut3);
                    System.out.println("PaymentSuccessful event sent.");
                    
                } catch (Exception e) {
                    Event eventOut = new Event("RequestPaymentFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "SuccessfullGotCustomerForCustomerID":
                System.out.println("Hello from the SuccessfullGotCustomerForCustomerId");
                Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Customer.class);
                Future_customer.complete(customer);
            case "SuccessfullGetMerchantByMerchantId":
                System.out.println("Hello from the SuccessfullGetMerchantByMerchantId");
                Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                Future_merchant.complete(merchant);
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
