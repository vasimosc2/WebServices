package services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.CustInt;
import models.Customer;
import jakarta.ws.rs.core.Response;

public class CustomerService implements EventReceiver {

    private List<Customer> customers = new ArrayList<>();
    
    private CompletableFuture<String> registerResult;
    private CompletableFuture<Customer> getCustomerResult;
    private CompletableFuture<Response> retireCustomer;

    private EventSender eventSender;
    private final Gson gson = new Gson();

    public CustomerService(EventSender eventSender) {
        this.eventSender = eventSender;
    }



    public List<Customer> getCustomers() {
        return customers;
    }


    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case "RegisterCustomerSuccessfull":
                System.out.println("I got a RegisterCustomerSuccessfull");
                String customerId = (String) eventIn.getArguments()[0];
                registerResult.complete(customerId); 
                break;
            case "RegisterCustomerFailed":
                registerResult.complete(null);
                break;
            case "GetCustomerSuccessfull":
                System.out.println("I got a GetCustomerSucessfull");
                Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Customer.class);
                getCustomerResult.complete(customer);
                break;
            case "GetCustomerFailed":
                getCustomerResult.complete(null);
                break;
            case "RetireCustomerByCprSuccessfull":
                System.out.println("I got a RetireCustomerByCprSuccessfull");
                boolean removed = customers.removeIf(c -> c.getCprNumber().equals((String) eventIn.getArguments()[0]));

                if (!removed) {
                    System.out.println("No customer found with CPR: " + eventIn.getArguments()[0]);
                }

                retireCustomer.complete(Response.status(200).entity("Delete successful").build());
                break;
            case "RetireCustomerByCprFailed":
                retireCustomer.complete(Response.status(404).entity("Delete successful").build());
                break;
            default:
                System.out.println("Ignored event in Rest with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }






    public String sendRegisterEvent(CustInt custInt) throws Exception{
        String eventType = "RegisterCustomer";
        Object[] arguments = new Object[]{custInt};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        String customerId = registerResult.join();
        
        Customer newCustomer = custInt.getCustomer();
        if(customerId!=null){
            newCustomer.setId(customerId);
            customers.add(newCustomer);
            return customerId;
        }
        return null;
    }



    public Customer getCustomerByCpr(String cprNumber) throws Exception {
        String eventType = "GetCustomer";
        Object[] arguments = new Object[]{cprNumber};
        Event event = new Event(eventType,arguments);
        getCustomerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        return getCustomerResult.join();
    }



    public Response retireAccount(String cprNumber) throws Exception {
        String eventType = "RetireCustomer";
        Object[] arguments = new Object[]{cprNumber};
        Event event = new Event(eventType,arguments);
        retireCustomer = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return retireCustomer.join();
    }
}