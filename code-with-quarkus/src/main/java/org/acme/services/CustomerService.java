package org.acme.services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.acme.models.CustInt;
import org.acme.models.Customer;

import com.google.gson.reflect.TypeToken;

import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import dtu.ws.fastmoney.User;

public class CustomerService implements EventReceiver {

    private List<Customer> customers = new ArrayList<>();
    
    private CompletableFuture<String> registerResult;
    private EventSender eventSender;

    public CustomerService(EventSender eventSender) {
        this.eventSender = eventSender;
    } // This is needed !!!



    public List<Customer> getCustomers() {
        return customers;
    }


    public void setCustomer(User user, String accountId) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(user.getFirstName());
        newCustomer.setLastName(user.getLastName());
        newCustomer.setCprNumber(user.getCprNumber());
        newCustomer.setBankAccount(accountId);
        customers.add(newCustomer);  // Adds the new payment to the list
    }




    public void receiveEvent(Event event) {
        switch (event.getEventType()) {
            case "RegisterSuccessful":
                System.out.println("I got the received message");
                String customerId = (String) event.getArguments()[0];
                registerResult.complete(customerId); // When this reach there thne the sendRegisterEvent is completed and the id is returned
                break;
            case "RegisterFailed":
                //String errorMessage = (String) event.getArguments()[0];
                registerResult.complete(null);
                break;
            default:
                System.out.println("Ignored event in Rest with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }






    public String sendRegisterEvent(CustInt custInt) throws Exception{
        String eventType = "RegisterCustomer"; // This will be caught at  customer-service/src/main/java/services/CustomerEventService.java
        Object[] arguments = new Object[]{custInt};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>(); // this I think will get the result in the future

        System.out.println("I am ready to send the message");
        eventSender.sendEvent(event);    // this how an event is send, at runtime the EventSender is becoming the RabbitMQ sender

        // Now we move to Customer Service and catch the eventType Register
        String customerId = registerResult.join();

        Customer newCustomer = custInt.getCustomer();
        if(customerId==null){
            newCustomer.setId(customerId);
            customers.add(newCustomer);
            return customerId;
        }
        return null;
    }
}