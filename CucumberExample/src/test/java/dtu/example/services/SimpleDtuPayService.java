package dtu.example.services;

import dtu.example.models.CustInt;
import dtu.example.models.Customer;
import dtu.example.models.MerchInt;
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
    
    public record BankPay(int money,Customer customer, Merchant merchant) {}
    
    public record UserAccountId(User user, String accountId) {}

    public String register(Customer customer,int money){
        CustInt custInt = new CustInt();
        custInt.setCustomer(customer);
        custInt.setMoney(money);
        Response response = target.path("customer").request().post(Entity.entity(custInt, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            String result = response.readEntity(new GenericType<>() {
            });

            return result.length() == 0 ? null : result;
        }
        return null;
    }


    public String register(Merchant merchant,int money){
        MerchInt merchInt = new MerchInt();
        merchInt.setMerchant(merchant);
        merchInt.setMoney(money);
        Response response = target.path("merchant").request().post(Entity.entity(merchInt, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            String result = response.readEntity(new GenericType<>() {
            });

            return result.length() == 0 ? null : result;
        }
        return null;
    }







    public Customer getCustomer(String cprNumber){
        Response response = target.path("customer/" + cprNumber).request().get();
        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<>() {
            });
        }

        return null;
    }

    public Response deleteCustomer(String cprNumber) {
        return target.path("customer/deleted/"+ cprNumber).request().delete();
    }



    public Merchant getMerchant(String cprNumber){
        Response response = target.path("merchant/" + cprNumber).request().get();
        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<>() {
            });
        }

        return null;
    }

    public Response deleteMerchant(String cprNumber) {
        return target.path("merchant/deleted/"+ cprNumber).request().delete();
    }



    public boolean maketransfer(int money,Customer customer, Merchant merchant) {
        target.path("payment").request().post(Entity.entity(new BankPay(money, customer, merchant), MediaType.APPLICATION_JSON));
        return true;
    }


    public Token requestTokenFromCustomer(String customerId) {
        return target.path("token").request().post(Entity.entity(customerId, MediaType.APPLICATION_JSON), Token.class);
    }


    public boolean requestTokens(String customerAccountId, int tokenAmount) {
        TokenInt tokenInt = new TokenInt();
        tokenInt.setAmount(tokenAmount);
        tokenInt.setCustomerId(customerAccountId);
        
        Response response = target.path("token").request().post(Entity.entity(tokenInt, MediaType.APPLICATION_JSON_TYPE));
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }




   
    
}
