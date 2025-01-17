package services;

import com.google.gson.Gson;

import dto.Customer;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.ICustomerService;

import java.util.List;
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
            case "Register":
                try {
                    Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), UserRegistrationDTO.class);
                    String internalId = service.register(customer);
                    Event eventOut = new Event("RegisterSuccessful", new Object[]{internalId});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RegisterFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetAccount":
                try {
                    String id = (String) eventIn.getArguments()[0];
                    Customer Customer = service.get(id);
                    Event eventOut = new Event("GetAccountSuccessful", new Object[]{Customer});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetAccountFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetAccountByCpr":
                try {
                    String cpr = (String) eventIn.getArguments()[0];
                    Customer Customer = service.getByCpr(cpr);
                    Event eventOut = new Event("GetAccountByCprSuccessful", new Object[]{Customer});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetAccountByCprFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "RetireAccount":
                try {
                    String id = (String) eventIn.getArguments()[0];
                    service.retireAccount(id);
                    Event eventOut = new Event("RetireAccountSuccessful");
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RetireAccountFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "RetireAccountByCpr":
                try {
                    String cpr = (String) eventIn.getArguments()[0];
                    service.retireAccountByCpr(cpr);
                    Event eventOut = new Event("RetireAccountByCprSuccessful");
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RetireAccountByCprFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetAllAccounts":
                try {
                    List<Customer> Customers = service.getAll();
                    Event eventOut = new Event("GetAllAccountsSuccessful", new Object[]{Customers});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetAllAccountsFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
