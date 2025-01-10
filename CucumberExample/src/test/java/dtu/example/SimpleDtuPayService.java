package dtu.example;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class SimpleDtuPayService {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8081/");

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
}
