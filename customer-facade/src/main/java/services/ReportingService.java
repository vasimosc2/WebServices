package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.PaymentCustomer;


import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static utils.EventTypes.*;
import static utils.EventTypes.CUSTOMER_REPORTS_REQUESTED;

public class ReportingService implements EventReceiver {

        private CompletableFuture<List<PaymentCustomer>> requestPaymentCustomerReportResult;
        private EventSender eventSender;
        private final Gson gson = new Gson();

        public ReportingService(EventSender eventSender)  {
            this.eventSender = eventSender;
        }

        @Override
        public void receiveEvent(Event eventIn) {
            switch (eventIn.getEventType()) {
                case CUSTOMER_REPORTS_GENERATED:
                    System.out.println("I got a CUSTOMER_REPORTS_GENERATED");
                    Type listType = new TypeToken<List<PaymentCustomer>>() {}.getType();
                    List<PaymentCustomer> customerReport = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), listType);
                    System.out.println("SANTI" + customerReport);
                    requestPaymentCustomerReportResult.complete(customerReport);
                    break;
                default:
                    System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                    break;
            }
        }


        public List<PaymentCustomer> sendRetrieveCustomerReportEvent(String customerId) throws Exception{
            String eventType = CUSTOMER_REPORTS_REQUESTED;
            System.out.println("we are sending CUSTOMER_REPORTS_REQUESTED event");
            Object[] arguments = new Object[]{customerId};
            Event event = new Event(eventType, arguments);
            requestPaymentCustomerReportResult = new CompletableFuture<>();
            eventSender.sendEvent(event);
            System.out.println("almost CUSTOMER_REPORTS_REQUESTED join");
            return requestPaymentCustomerReportResult.join();

        }

}
