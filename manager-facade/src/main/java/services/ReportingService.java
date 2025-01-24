/**
 * @primary-author Marcu Muro (s233662)
 * @co-author Kaizhi Fan (s240047)
 *
 *
 */
package services;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.Payment;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static utils.EventTypes.*;

public class ReportingService implements EventReceiver {

        private CompletableFuture<List<Payment>> requestPaymentManagerReportResult;
        private EventSender eventSender;
        private final Gson gson = new Gson();

        public ReportingService(EventSender eventSender)  {
            this.eventSender = eventSender;
        }

        @Override
        public void receiveEvent(Event eventIn) {
            switch (eventIn.getEventType()) {
                case MANAGER_REPORTS_GENERATED:
                    System.out.println("I got a MANAGER_REPORTS_GENERATED");
                    Type listType = new TypeToken<List<Payment>>() {}.getType();
                    List<Payment> managerReport = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), listType);
                    System.out.println("SANTI" + managerReport);
                    requestPaymentManagerReportResult.complete(managerReport);
                    break;
                default:
                    System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                    break;
            }
        }


        public List<Payment> sendRetrieveManagerReportEvent() throws Exception{
            String eventType = MANAGER_REPORTS_REQUESTED;
            System.out.println("we are sending MANAGER_REPORTS_REQUESTED event");
            // Object[] arguments = new Object[]{customerId};
            Event event = new Event(eventType);
            requestPaymentManagerReportResult = new CompletableFuture<>();
            eventSender.sendEvent(event);
            System.out.println("almost MANAGER_REPORTS_REQUESTED join");
            return requestPaymentManagerReportResult.join();

        }

}
