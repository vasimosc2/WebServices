package messaging.rabbitmq;
import messaging.EventSender;
import org.acme.services.CustomerService;


public class CustomerFactory {

    static CustomerService customerService = null;

    public static CustomerService getService() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (customerService != null) {
            return customerService;
        }

        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        EventSender sender = new CustomerSender();
        customerService = new CustomerService(sender);
        CustomerListener r = new CustomerListener(customerService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return customerService;
    }
}
