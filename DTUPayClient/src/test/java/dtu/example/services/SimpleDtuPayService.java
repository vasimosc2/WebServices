package dtu.example.services;

import java.util.List;

import dtu.example.models.Customer;
import dtu.example.models.Token;
import dtu.example.models.Merchant;
import dtu.example.models.Stakeholder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
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
        target.path("payments")
                    .request()
                    .post(Entity.entity(new BankPay(money, customerId, merchantId), MediaType.APPLICATION_JSON));
        return true;
    }

    public record TokenRequest(String customerId, int count){}

    public void generateTokens(String customerId, int count){
        Response response = target.path("tokens")
                                    .request()
                                    .post(Entity.entity(new TokenRequest(customerId, count), MediaType.APPLICATION_JSON));
    }
   
    public List<Token> getAllTokens(){
        Response response = target.path("tokens")
                                    .request()
                                    .get();
        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<Token>>(){});
        }else{
            return List.of();
        }
    }

//    public boolean retireToken(String tokenId) {
//        Response response = target.path("tokens")
//                                  .path(tokenId)
//                                  .request()
//                                  .delete();
//        return response.getStatus() == 204;
//    }

    public Token validateToken(String tokenId) {
        Response response = target.path("tokens")
                                  .path(tokenId)
                                  .path("validate")
                                  .request()
                                  .get();
        if (response.getStatus() == 200) {
            return response.readEntity(Token.class);
        } else {
            throw new RuntimeException("Token invalid: " + response.readEntity(String.class));
        }
    }
    
    
}
