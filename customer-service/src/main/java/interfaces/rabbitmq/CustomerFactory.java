/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package interfaces.rabbitmq;
import messaging.EventSender;
import services.CustomerEventService;
import services.CustomerService;


public class CustomerFactory {

    static CustomerEventService customerEventService = null;

    public CustomerEventService getService() {
        if (customerEventService != null) {
            return customerEventService;
        }
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
