/**
 * @primary-author Angelos Michelis (s232488)
 * @co-author Kaizhi Fan (s240047)
 *
 *
 */
package messaging.rabbitmq.customer;
import messaging.EventSender;
import services.CustomerService;


public class CustomerFactory {

    static CustomerService customerService = null;

    public static CustomerService getService() {
        if (customerService != null) {
            return customerService;
        }

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
