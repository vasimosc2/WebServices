/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package interfaces.rabbitmq.payment;
import messaging.EventSender;
import services.PaymentEventService;
import services.PaymentService;


public class PaymentFactory {

    static PaymentEventService PaymentEventService = null;

    public PaymentEventService getService() {

        if (PaymentEventService != null) {
            return PaymentEventService;
        }

        
        EventSender b = new PaymentSender();
        PaymentEventService = new PaymentEventService(b, new PaymentService());
        PaymentListener r = new PaymentListener(PaymentEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return PaymentEventService;
    }
}
