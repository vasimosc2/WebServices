package dtu.example.services;

import java.math.BigDecimal;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Payment;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class SimpleDtuPayService {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://fm-11.compute.dtu.dk:8080/");
    

    public record CustInt(Customer customer, int value) {}
    public record MerchInt(Merchant merchant, int value) {}
    public record BankPay(int money,Customer customer, Merchant merchant) {}


    public void registerBank(Customer customer, int money) {
        target.path("customer").request().post(Entity.entity(new CustInt(customer, money), MediaType.APPLICATION_JSON));
    }

    public String register(Customer customer){
        target.path("customer").request().put(Entity.entity(customer, MediaType.APPLICATION_JSON));
        return customer.getId();
    }

    public void registerBank(Merchant merchant, Integer int1) {
        target.path("merchant").request().post(Entity.entity(new MerchInt(merchant,int1), MediaType.APPLICATION_JSON));
    }


    public String register(Merchant merchant) {
        target.path("merchant").request().put(Entity.entity(merchant, MediaType.APPLICATION_JSON));
        return merchant.getId();
    }


    public boolean maketransfer(int money,Customer customer, Merchant merchant) {
        Payment payment = new Payment(money,customer.getId(),merchant.getId());
        target.path("payment").request().post(Entity.entity(new BankPay(money, customer, merchant), MediaType.APPLICATION_JSON));
        target.path("payment").request().put(Entity.entity(payment, MediaType.APPLICATION_JSON));
        return true;
    }


    public BigDecimal Money(Customer customer) {
         target.path("bank").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
         return target.path("bank").request().get(BigDecimal.class);

    }

    public Object Money(Merchant merchant) {
        target.path("bank").request().post(Entity.entity(merchant, MediaType.APPLICATION_JSON));
        return target.path("bank").request().get(BigDecimal.class);
    }
    public void delete() {
        target.path("customer").request().delete();
        target.path("merchant").request().delete();
    }
    
}
