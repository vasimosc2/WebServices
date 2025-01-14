package dtu.example.services;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class SimpleDtuPayService {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8081/");
    
    public record BankPay(int money,Customer customer, Merchant merchant) {}
    public record UserAccountId(User user, String accountId) {}
    public void register(User user,String accountId, String path) {
        target.path(path).request().put(Entity.entity(new UserAccountId(user, accountId), MediaType.APPLICATION_JSON));
    }


    public boolean maketransfer(int money,Customer customer, Merchant merchant) {
        target.path("payment").request().post(Entity.entity(new BankPay(money, customer, merchant), MediaType.APPLICATION_JSON));
        return true;
    }


   
    
}
