/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package services;
import com.google.gson.Gson;

import exceptions.account.AccountExistsException;
import models.Customer;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.ICustomerService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utils.EventTypes.*;

public class CustomerEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(CustomerEventService.class.getName());

    private final EventSender eventSender;
    
    private final ICustomerService service;
    private final Gson gson = new Gson();

    public CustomerEventService(EventSender eventSender, CustomerService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case REGISTER_CUSTOMER_REQUEST:
                try {
                System.out.println("Hello from RegisterCustomer");
                Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Customer.class);
                System.out.println(customer.getFirstName());

                String customerId = service.register(customer);
                System.out.println(customerId);

                Event eventOut = new Event(REGISTER_CUSTOMER_REQUEST_SUCCESS, new Object[]{customerId});
                eventSender.sendEvent(eventOut);

                } catch (AccountExistsException ex) {
                    System.out.println("Registration failed: " + ex.getMessage());
                    Event eventOut = new Event(REGISTER_CUSTOMER_REQUEST_FAILED, new Object[]{"Registration Failed"});
                    eventSender.sendEvent(eventOut);

                } catch (Exception e) {
                    // Handle other general exceptions
                    System.err.println("Unexpected error: " + e.getMessage());
                    Event eventOut = new Event("RegisterCustomerFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
            break;
            case GET_CUSTOMER_REQUEST:
                try {
                    System.out.println("Hello from GetCustomer");
                    String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(String.format("I am at CustomerEventService: %s", customerId));

                    Customer Customer = service.getCustomerById(customerId);
                    Event eventOut = new Event(GET_CUSTOMER_REQUEST_SUCCESS, new Object[]{Customer});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(GET_CUSTOMER_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            
            case GET_CUSTOMER_BY_CUSTOMER_ID_REQUEST:
                try {
                    System.out.println("Hello from GET_CUSTOMER_BY_CUSTOMER_ID_REQUEST");
                    String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    String correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), String.class);
                    System.out.println(String.format("I am at CustomerEventService: %s", customerId));

                    Customer Customer = service.getCustomerById(customerId);
                    System.out.println("I found the customer and I will send GET_CUSTOMER_BY_CUSTOMER_ID_REQUEST_SUCCESS .....");
                    System.out.println(Customer.getFirstName());
                    Event eventOut = new Event(GET_CUSTOMER_BY_CUSTOMER_ID_REQUEST_SUCCESS, new Object[]{Customer,correlationId});
                    eventSender.sendEvent(eventOut);

                } catch (Exception e) {
                    Event eventOut = new Event(GET_CUSTOMER_BY_CUSTOMER_ID_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;

            case RETIRE_CUSTOMER_REQUEST:
                try {
                    System.out.println("Hello from RetireCustomer");
                    String customerId = (String) eventIn.getArguments()[0];
                    String customerIDResponse = service.retireAccount(customerId);
                    System.out.println("customerId input: " + customerId + " customerId output: " + customerIDResponse);

                    Event eventOut = new Event(RETIRE_CUSTOMER_REQUEST_SUCCESS, new Object[]{customerIDResponse});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(RETIRE_CUSTOMER_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
