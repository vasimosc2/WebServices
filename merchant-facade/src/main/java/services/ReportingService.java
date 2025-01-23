package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.PaymentMerchant;


import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static utils.EventTypes.*;

public class ReportingService implements EventReceiver {

        private CompletableFuture<List<PaymentMerchant>> requestPaymentMerchantReportResult;
        private EventSender eventSender;
        private final Gson gson = new Gson();

        public ReportingService(EventSender eventSender)  {
            this.eventSender = eventSender;
        }

        @Override
        public void receiveEvent(Event eventIn) {
            switch (eventIn.getEventType()) {
                case MERCHANT_REPORTS_GENERATED:
                    System.out.println("I got a MERCHANT_REPORTS_GENERATED");
                    Type listType = new TypeToken<List<PaymentMerchant>>() {}.getType();
                    List<PaymentMerchant> merchantReport = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), listType);
                    System.out.println("SANTI" + merchantReport);
                    requestPaymentMerchantReportResult.complete(merchantReport);
                    break;
                default:
                    System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                    break;
            }
        }


        public List<PaymentMerchant> sendRetrieveMerchantReportEvent(String customerId) throws Exception{
            String eventType = MERCHANT_REPORTS_REQUESTED;
            System.out.println("we are sending MERCHANT_REPORTS_REQUESTED event");
            Object[] arguments = new Object[]{customerId};
            Event event = new Event(eventType, arguments);
            requestPaymentMerchantReportResult = new CompletableFuture<>();
            eventSender.sendEvent(event);
            System.out.println("almost MERCHANT_REPORTS_REQUESTED join");
            return requestPaymentMerchantReportResult.join();

        }

}
