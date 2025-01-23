package messaging.rabbitmq.payment;
import messaging.EventSender;
import services.PaymentService;


public class PaymentFactory {

    static PaymentService paymentService = null;

    public static PaymentService getService() {
        if (paymentService != null) {
            return paymentService;
        }

        EventSender sender = new PaymentSender();
        paymentService = new PaymentService(sender);
        PaymentListener r = new PaymentListener(paymentService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return paymentService;
    }
}
