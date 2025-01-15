package org.acme.services;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.Customer;
import dtu.ws.fastmoney.User;
import org.apache.commons.lang3.RandomStringUtils;

@ApplicationScoped
public class CustomerService {

    private List<Customer> customers = new ArrayList<>();


    public List<Customer> getCustomers() {
        return customers;
    }

    public String setCustomer(Customer customer) {
        String customerId;
        do {
            customerId = "CUST-" + RandomStringUtils.randomNumeric(8);
        } while (isCustomerIdPresent(customerId));

        customer.setId(customerId);
        customers.add(customer); // Add the customer to the list

        //Customer customerTEST = getCustomer(customerId);

        //System.out.println("SANTI Just added the following customer: " + customerTEST.getId() + " with the bank account: " + customerTEST.getBankAccount());

        return customerId; // Return the unique customerId
    }

    private boolean isCustomerIdPresent(String customerId) {
        // Check if any customer in the list has the same customerId
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String customerId) {
        System.out.println("SANTI list of customers in list: " + customers.size());
        System.out.println("SANTI customer name: " + customers.get(0).getFirstName());
        System.out.println("SANTI customer ID: " + customers.get(0).getId());
        System.out.println("SANTI customer bank account: " + customers.get(0).getBankAccount());


        System.out.println("SANTI Searching for customer with ID: " + customerId);
        for (Customer c : customers) {
            System.out.println("SANTI Checking customer with ID: " + c.getId());
            if (c.getId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
}