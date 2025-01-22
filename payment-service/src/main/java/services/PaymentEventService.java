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

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.Response;

import static utils.EventTypes.*;

public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(PaymentEventService.class.getName());

    private final EventSender eventSender;
    
    private final IPaymentService service;

    private CompletableFuture<Customer> Future_customer  = new CompletableFuture<>();
    private CompletableFuture<Merchant> Future_merchant =  new CompletableFuture<>();
    private BigDecimal amount;

    private final Gson gson = new Gson();

    public PaymentEventService(EventSender eventSender, PaymentService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case PAYMENT_REQUEST:
                try {
                    System.out.println("Hello from RequestPayment");
                    BankPay bankPay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);

                    System.out.println(bankPay.getMoney());
                    System.out.println(bankPay.getMerchantId());
                    System.out.println(bankPay.getTokenId());

                    Future_customer = new CompletableFuture<>();

                    // Step 2: Send event to retrieve Customer
                    Event eventOut1 = new Event(GET_CUSTOMER_ID_BY_TOKEN_ID_REQUEST, new Object[]{bankPay.getTokenId()});
                    System.out.println("Sending to the Token Service .....");
                    eventSender.sendEvent(eventOut1);

                    // Step 3: Chain actions after Customer retrieval
                    Future_customer.thenAcceptAsync(customer -> {
                        try {
                            System.out.println("I got a customer: " + customer.getFirstName());

                            // Step 4: Send event to retrieve Merchant
                            Future_merchant = new CompletableFuture<>();
                            Event eventOut2 = new Event(GET_MERCHANT_BY_MERCHANT_ID_REQUEST, new Object[]{bankPay.getMerchantId()});
                            System.out.println("Sending to the Merchant Service ....");
                            eventSender.sendEvent(eventOut2);

                            // Step 5: Chain actions after Merchant retrieval
                            Future_merchant.thenAcceptAsync(merchant -> {
                                try {
                                    System.out.println("I got the merchant: " + merchant.getFirstName());

                                    service.requestPayment(customer, merchant, bankPay.getMoney(), bankPay.getTokenId());

                                    // Step 6: Send success event
                                    Event eventOut3 = new Event(PAYMENT_REQUEST_SUCCESS, new Object[]{bankPay.getTokenId()});
                                    System.out.println("I am sending a PaymentSuccessful ....");
                                    eventSender.sendEvent(eventOut3);
                                } catch (Exception e) {
                                    System.err.println("Error in merchant processing: " + e.getMessage());
                                }
                            });
                        } catch (Exception e) {
                            System.err.println("Error in customer processing: " + e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    System.err.println("Error during RequestPayment: " + e.getMessage());
                }
                break;
            case GET_CUSTOMER_BY_CUSTOMER_ID_REQUEST_SUCCESS:
                System.out.println("Hello from the SuccessfullGotCustomerForCustomerId");
                Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Customer.class);
                System.out.println("I am at the Paymentservice and the Customer I found is :");
                System.out.println(customer.getFirstName());
                Future_customer.complete(customer);
                break;
            case GET_MERCHANT_BY_MERCHANT_ID_REQUEST_SUCCESS:
                System.out.println("Hello from the SuccessfullGetMerchantByMerchantId");
                Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
                Future_merchant.complete(merchant);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }

//    public Customer sendRequestCustomerByTokenIdEvent(String tokenId) throws Exception{
//        String eventType = "RequestCustomerByTokenEvent";
//        Object[] arguments = new Object[]{tokenId};
//        Event event = new Event(eventType, arguments);
//        CompletableFuture<Customer> requestCustomerByTokenIdResult = new CompletableFuture<>();
//        eventSender.sendEvent(event);
//
//        return requestCustomerByTokenIdResult.join();
//    }

}
