package dtu.example.services;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Token;
import dtu.example.models.TokenInt;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SimpleDtuPayService {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8081/");
    
    public record BankPay(int money, String tokenId, String merchantId) {}
    
    public record UserAccountId(User user, String accountId) {}

    public String register(Customer customer){
        Response response = target.path("customers")
                                    .request()
                                    .post(Entity.entity(customer, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            String result = response.readEntity(new GenericType<>() {
            });

            return result.length() == 0 ? null : result;
        }
        return null;
    }


    public String register(Merchant merchant){
        Response response = target.path("merchants")
                                .request()
                                .post(Entity.entity(merchant, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            String result = response.readEntity(new GenericType<>() {
            });

            return result.length() == 0 ? null : result;
        }
        return null;
    }







    public Customer getCustomer(String cprNumber){
        Response response = target.path("customers/" + cprNumber).request().get();
        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<>() {
            });
        }

        return null;
    }

    public Response deleteCustomer(String cprNumber) {
        return target.path("customers/deleted/"+ cprNumber).request().delete();
    }



    public Merchant getMerchant(String cprNumber){
        Response response = target.path("merchants/" + cprNumber).request().get();
        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<>() {
            });
        }

        return null;
    }

    public Response deleteMerchant(String cprNumber) {
        return target.path("merchants/deleted/"+ cprNumber).request().delete();
    }



    public boolean maketransfer(int money, String tokenId, String merchantId) {
        Response response = target.path("payments")
                                    .request()
                                    .post(Entity.entity(new BankPay(money, tokenId, merchantId), MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            return false;
        }
        return true;
    }


    public Token requestTokenFromCustomer(String customerId) {
        return target.path("tokens")
                .request()
                .post(Entity.entity(customerId, MediaType.APPLICATION_JSON), Token.class);
    }


    public boolean generateTokens(String customerAccountId, int tokenAmount) {
        TokenInt tokenInt = new TokenInt();
        tokenInt.setAmount(tokenAmount);
        tokenInt.setCustomerId(customerAccountId);
        
        Response response = target.path("tokens")
                                    .request()
                                    .post(Entity.entity(tokenInt, MediaType.APPLICATION_JSON_TYPE));

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }




   
    
}
