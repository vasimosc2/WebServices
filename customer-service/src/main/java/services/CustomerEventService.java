package services;

import com.google.gson.Gson;

import models.CustInt;
import models.Customer;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.ICustomerService;

import java.util.logging.Level;
import java.util.logging.Logger;

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
            case "RegisterCustomer":
                try {
                    System.out.println("Hello from RegisterCustomer");
                    CustInt custInt = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), CustInt.class);
                    System.out.println(custInt.getMoney());
                    System.out.println(custInt.getCustomer().getFirstName());
                    String customerId = service.register(custInt);
                    System.out.println(customerId);
                    Event eventOut = new Event("RegisterCustomerSuccessfull", new Object[]{customerId});

                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RegisterCustomerFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetCustomer":
                try {
                    System.out.println("Hello from GetCustomer");
                    String cprNumber = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(String.format("I am at CustomerEventService: %s", cprNumber));

                    Customer Customer = service.get(cprNumber);
                    Event eventOut = new Event("GetCustomerSuccessfull", new Object[]{Customer});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetCustomerFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            
            case "RetireCustomer":
                try {
                    System.out.println("Hello from RetireCustomer");
                    String cpr = (String) eventIn.getArguments()[0];
                    String customerId = service.retireAccountByCpr(cpr);

                    Event eventOut = new Event("RetireCustomerByCprSuccessfull", new Object[]{customerId});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RetireCustomerByCprFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
