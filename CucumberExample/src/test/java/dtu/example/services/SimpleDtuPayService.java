package dtu.example.services;

import dtu.example.models.CustInt;
import dtu.example.models.Customer;
import dtu.example.models.Merchant;
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

    public void register(User user,String accountId, String path) {
        target.path(path).request().put(Entity.entity(new UserAccountId(user, accountId), MediaType.APPLICATION_JSON));
    }


    public boolean maketransfer(int money,Customer customer, Merchant merchant) {
        target.path("payment").request().post(Entity.entity(new BankPay(money, customer, merchant), MediaType.APPLICATION_JSON));
        return true;
    }


   
    
}
