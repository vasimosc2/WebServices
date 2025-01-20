package dtu.example.services;

import java.util.List;

import dtu.example.exceptions.PaymentException;
import dtu.example.models.Token;
import dtu.example.models.Stakeholder;
import dtu.example.models.TokenRequest;
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
    
    public record BankPay(int money, String tokenId, String merchantId) {}

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


    public boolean makeTransfer(int money, String tokenId, String merchantId) throws PaymentException {

        Response response = target.path("payments")
                    .request()
                    .post(Entity.entity(new BankPay(money, tokenId, merchantId), MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            throw new PaymentException(response.readEntity(String.class));
            return false;
        }

        return true;
    }


    public List<Token> generateTokens(String customerId, int count){
        Response response = target.path("tokens")
                                    .request()
                                    .post(Entity.entity(new TokenRequest(customerId, count), MediaType.APPLICATION_JSON));

        return response.readEntity(new GenericType<List<Token>>() {});
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
