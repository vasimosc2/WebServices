package services;
import com.google.gson.Gson;

import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
import models.Customer;
import models.Merchant;
import models.MoneyTransferredObject;
import services.interfaces.IPaymentService;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.EventTypes.*;

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
            case PAYMENT_REQUEST:
                try {
                    System.out.println("Hello from RequestPayment at the PaymentService");
                    BankPay bankPay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);
                    
                    Future_customer = new CompletableFuture<>();
                    Future_merchant = new CompletableFuture<>();

                    System.out.println("Futures have been created. Waiting for them to be completed...");

                    CompletableFuture<Void> allFutures = CompletableFuture.allOf(Future_customer, Future_merchant);

                    allFutures.thenAcceptAsync(voidResult -> {
                        try {
                            // Retrieve the results from the futures
                            Customer customer = Future_customer.get();
                            Merchant merchant = Future_merchant.get();

                            System.out.println("I got a customer: " + customer.getFirstName());
                            System.out.println("I got the merchant: " + merchant.getFirstName());

                            // Step 4: Process the payment
                            MoneyTransferredObject moneyTransfer = service.requestPayment(customer, merchant, bankPay.getMoney(), bankPay.getTokenId()); // TODO potential a bool for that

                            // Step 5: Send success event

                            Event eventOut1 = new Event(PAYMENT_REQUEST_SUCCESS, new Object[]{});
                            Event eventOut2 = new Event(MONEY_TRANSFERRED, new Object[]{moneyTransfer});

                            System.out.println("I am sending a PaymentSuccessful to Report and MerchantFacede ....");
                            eventSender.sendEvent(eventOut1);
                            eventSender.sendEvent(eventOut2);

                            
                        } catch (Exception e) {
                            System.err.println("Error processing payment: " + e.getMessage());
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

}
