/**
 * @primary-author Vasileios Moschou (s222566)
 *  @co-author Angelos Michelis (s232488)
 *
 */


package services;
import com.google.gson.Gson;

import exceptions.PaymentException;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Customer;
import models.Merchant;
import models.MoneyTransferredObject;
import services.interfaces.IPaymentService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.EventTypes.*;

public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(PaymentEventService.class.getName());

    private final EventSender eventSender;
    private final IPaymentService service;

    // Maps to store futures for each correlationId
    private final Map<String, CompletableFuture<Customer>> correlationCustomer = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<Merchant>> correlationMerchant = new ConcurrentHashMap<>();

    private final Gson gson = new Gson();

    public PaymentEventService(EventSender eventSender, PaymentService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case PAYMENT_REQUESTED:
                handlePaymentRequest(eventIn);
                break;
            case CUSTOMER_BY_CUSTOMER_ID_RETRIEVED:
                handleCustomerSuccess(eventIn);
                break;
            case MERCHANT_BY_MERCHANT_ID_RETRIEVED:
                handleMerchantSuccess(eventIn);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }

    private void handlePaymentRequest(Event eventIn) throws Exception{
        try {
            System.out.println("Hello from PAYMENT_REQUESTED at the PaymentService");
            BankPay bankPay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);
            String correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), String.class);

            // Initialize futures for this correlationId
            correlationCustomer.put(correlationId, new CompletableFuture<>());
            correlationMerchant.put(correlationId, new CompletableFuture<>());

            System.out.println("Futures have been created. Waiting for them to be completed...");

            // Wait for both futures to complete
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    correlationCustomer.get(correlationId),
                    correlationMerchant.get(correlationId)
            );

            allFutures.thenAcceptAsync(voidResult -> {
                try {
                    // Retrieve the results from the futures
                    Customer customer = correlationCustomer.get(correlationId).get();
                    Merchant merchant = correlationMerchant.get(correlationId).get();

                    System.out.println("I got a customer: " + customer.getFirstName());
                    System.out.println("And the customer bankaccount is" + customer.getBankAccount());
                    System.out.println("I got the merchant: " + merchant.getFirstName());
                    System.out.println("And the merchant bankaccount is" + merchant.getBankAccount());
                    
                     try {
                    // Process the payment
                    MoneyTransferredObject moneyTransfer = service.requestPayment(customer, merchant, bankPay.getMoney(), bankPay.getTokenId());

                    // Send success events
                    Event eventOut1 = new Event(PAYMENT_HAS_SUCCEEDED, new Object[]{correlationId});
                    Event eventOut2 = new Event(MONEY_TRANSFERRED, new Object[]{moneyTransfer});

                    System.out.println("I am sending a PaymentSuccessful to Report and MerchantFacade ....");
                    eventSender.sendEvent(eventOut1);
                    eventSender.sendEvent(eventOut2);
                    } catch (PaymentException e) {
                        System.out.println("Payment failed: " + e.getMessage());

                        // Send a bad request event
                        Event badRequestEvent = new Event(PAYMENT_HAS_FAILED, new Object[]{correlationId});
                        System.out.println("I am sending a PaymentFailed event due to: " + e.getMessage());
                        eventSender.sendEvent(badRequestEvent);
                }

                } catch (Exception e) {
                    System.err.println("Error processing payment: " + e.getMessage());
                } finally {
                    correlationCustomer.remove(correlationId);
                    correlationMerchant.remove(correlationId);
                }
            });
        } catch (Exception e) {
            System.err.println("Error during PAYMENT_REQUESTED: " + e.getMessage());
            Event eventOut = new Event(PAYMENT_HAS_FAILED, new Object[]{e.getMessage()});
            eventSender.sendEvent(eventOut);
        }
    }

    private void handleCustomerSuccess(Event eventIn) {
        System.out.println("Hello from the CUSTOMER_BY_CUSTOMER_ID_RETRIEVED");
        Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Customer.class);
        String correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), String.class);

        CompletableFuture<Customer> futureCustomer = correlationCustomer.get(correlationId);
        if (futureCustomer != null) {
            futureCustomer.complete(customer);
        } else {
            System.err.println("No customer future found for correlationId: " + correlationId);
        }
    }

    private void handleMerchantSuccess(Event eventIn) {
        System.out.println("Hello from the MERCHANT_BY_MERCHANT_ID_RETRIEVED");
        Merchant merchant = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Merchant.class);
        String correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), String.class);

        CompletableFuture<Merchant> futureMerchant = correlationMerchant.get(correlationId);
        if (futureMerchant != null) {
            futureMerchant.complete(merchant);
        } else {
            System.err.println("No merchant future found for correlationId: " + correlationId);
        }
    }
}