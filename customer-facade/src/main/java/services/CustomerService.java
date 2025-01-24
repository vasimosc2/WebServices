package services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.Customer;
import jakarta.ws.rs.core.Response;

import static utils.EventTypes.*;

public class CustomerService implements EventReceiver {

    private List<String> customerIds = new ArrayList<>();
    private CompletableFuture<String> registerResult;
    private CompletableFuture<Customer> getCustomerResult;
    private CompletableFuture<Response> retireCustomer;

    private EventSender eventSender;
    private final Gson gson = new Gson();

    public CustomerService(EventSender eventSender) {
        this.eventSender = eventSender;
    }



    public List<String> getCustomers() {
        return customerIds;
    }


    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case REGISTER_CUSTOMER_REQUEST_SUCCESS:
                System.out.println("I got a RegisterCustomerSuccessfull");
                String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                registerResult.complete(customerId); 
                break;

            case REGISTER_CUSTOMER_REQUEST_FAILED:
                System.out.println("I got a Failed RegisterCustomer");
                String errormessage = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                registerResult.complete(errormessage);
                break;

            case GET_CUSTOMER_REQUEST_SUCCESS:
                System.out.println("I got a GetCustomerSucessfull");
                Customer customer = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Customer.class);
                getCustomerResult.complete(customer);
                break;

            case GET_CUSTOMER_REQUEST_FAILED:
                getCustomerResult.complete(null);
                break;

            case RETIRE_CUSTOMER_REQUEST_SUCCESS:
                System.out.println("I got a RetireCustomerByCprSuccessfull");
                boolean removed = customerIds.removeIf(c -> c.equals((String) eventIn.getArguments()[0]));

                if (!removed) {
                    System.out.println("No customer found with CPR: " + eventIn.getArguments()[0]);
                }
                retireCustomer.complete(Response.status(200).entity("Delete successful").build());
                break;

            case RETIRE_CUSTOMER_REQUEST_FAILED:
                retireCustomer.complete(Response.status(404).entity("Delete not successful").build());
                break;

            default:
                System.out.println("Ignored event in Rest with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }






    public String sendRegisterEvent(Customer customer) throws Exception{
        String eventType = REGISTER_CUSTOMER_REQUEST;
        Object[] arguments = new Object[]{customer};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        String customerId = registerResult.join();
    

        if(customerId!=null){
            customerIds.add(customerId);
            return customerId;
        }
        return null;
    }



    public Customer getCustomerByCustomerId(String customerId) throws Exception {
        String eventType = GET_CUSTOMER_REQUEST;
        Object[] arguments = new Object[]{customerId};
        Event event = new Event(eventType,arguments);
        getCustomerResult = new CompletableFuture<>();

        eventSender.sendEvent(event);

        return getCustomerResult.join();
    }



    public Response retireAccount(String customerId) throws Exception {
        String eventType = RETIRE_CUSTOMER_REQUEST;
        Object[] arguments = new Object[]{customerId};
        Event event = new Event(eventType,arguments);
        retireCustomer = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return retireCustomer.join();
    }
}