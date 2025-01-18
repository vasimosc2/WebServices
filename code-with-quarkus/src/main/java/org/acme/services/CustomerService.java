package org.acme.services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.acme.models.CustInt;
import org.acme.models.Customer;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import dtu.ws.fastmoney.User;

public class CustomerService {

    private List<Customer> customers = new ArrayList<>();
    private CompletableFuture<String> registerResult;
    private EventSender eventSender;






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

    public String sendRegisterEvent(CustInt custInt) throws Exception{
        String eventType = "Register";
        Object[] arguments = new Object[]{custInt};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>(); // this I think will get the result in the future

        eventSender.sendEvent(event);// this how an event is send, at runtime the EventSender is becoming the RabbitMQ sender

        // Now we move to Customer Service and catch the eventType Register
        String id = registerResult.join();

        return id;
    }
}