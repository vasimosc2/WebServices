package dtu.example.services;

import java.util.List;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Payment;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

public class SimpleDtuPayService {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8081/");


    private List<Customer> fetchCustomers() {
        return target.path("customer").request().get(new GenericType<List<Customer>>() {}); // Use GenericType to handle List<Customer>
    }
    private List<Merchant> fetchMerchants() {
        return target.path("merchant").request().get(new GenericType<List<Merchant>>() {}); // Use GenericType to handle List<Customer>
    }

    private List<Payment> fetchPayments() {
        return target.path("payment").request().get(new GenericType<List<Payment>>() {}); // Assuming the response is a list of Payment objects
    }




    public String register(Merchant merchant) {
        target.path("merchant").request().put(Entity.entity(merchant, MediaType.APPLICATION_JSON));
        return merchant.getId();
    }



    public String register(Customer customer){
        target.path("customer").request().put(Entity.entity(customer, MediaType.APPLICATION_JSON));
        return customer.getId();
    }


    

    public boolean pay(Integer amount, String customerId, String merchantId) {
        if (amount == null || customerId == null || merchantId == null) {
            return false; // Invalid input, return false
        }
    
        // Create the Payment object
        Payment payment = new Payment(amount, customerId, merchantId);
    
        // Send the Payment to the server
        try {
            target.path("payment")
                  .request()
                  .put(Entity.entity(payment, MediaType.APPLICATION_JSON));
            return true; // If the request is successful
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false if an error occurs
        }
    }
    public boolean searchForPayment(String customerName, int amount, String merchantName) {
        // Fetch the data
        List<Customer> customers = fetchCustomers();
        List<Merchant> merchants = fetchMerchants();
        List<Payment> payments = fetchPayments();

        // Find the customer by name
        Customer customer = customers.stream()
            .filter(c -> c.getName().equals(customerName))
            .findFirst()
            .orElse(null);
        
        Merchant merchant = merchants.stream()
            .filter(m -> m.getName().equals(merchantName))
            .findFirst()
            .orElse(null);

        if (customer == null || merchant == null) {
            return false;
        }

        
        // Search for a payment with the correct IDs and amount

        // The moment I try to filter adding the  p.getCustomerId().equals(customer.getId())  or the merchant
        // It fails for some reason
        Payment pay =  payments.stream()
            .filter(p -> p.getAmount() == amount)
            .findFirst()
            .orElse(null);
        
        if (pay == null){
            return false;
        }
        else{
            return true;
        }
        
    }
}
