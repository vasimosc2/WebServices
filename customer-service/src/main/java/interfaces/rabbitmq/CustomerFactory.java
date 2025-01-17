package interfaces.rabbitmq;

import messaging.EventSender;
import services.CustomerEventService;
import services.CustomerService;


public class CustomerFactory {

    static CustomerEventService customerEventService = null;

    public CustomerEventService getService() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (customerEventService != null) {
            return customerEventService;
        }

        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        EventSender b = new CustomerSender();
        customerEventService = new CustomerEventService(b, new CustomerService());
        CustomerListener r = new CustomerListener(customerEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return customerEventService;
    }
}
