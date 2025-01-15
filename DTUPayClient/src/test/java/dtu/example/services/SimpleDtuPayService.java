package dtu.example.services;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Stakeholder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SimpleDtuPayService {

    Client client = ClientBuilder.newClient();

    String baseUrl = "http://localhost:8080/";
    WebTarget target = client.target(baseUrl);
    
    public record BankPay(int money, String customerId, String merchantId) {}

    public String registerCustomer(Stakeholder stakeholder) {
        return registerUser(stakeholder, "customers");
    }

    public String registerMerchant(Stakeholder stakeholder) {
        return registerUser(stakeholder, "merchants");
    }
    private String registerUser(Stakeholder stakeholder, String path) {

        Response response = target.path(path)
                .request()
                .post(Entity.entity(stakeholder, MediaType.APPLICATION_JSON));

        return response.readEntity(String.class);
    }


    public boolean makeTransfer(int money,String customerId, String merchantId) {
        target.path("payments").
                request().
                post(Entity.entity(new BankPay(money, customerId, merchantId), MediaType.APPLICATION_JSON));
        return true;
    }


   
    
}
