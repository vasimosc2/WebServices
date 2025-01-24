package dtu.example.services;

import dtu.example.models.Customer;
import dtu.example.models.Payment;
import dtu.example.models.PaymentCustomer;
import dtu.example.models.Token;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


public class ManagerFacadeClient {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8089/");

     public List<Payment> getManagerPaymentReport() {
            System.out.println("request payment report for manager");
            Response response = target.path("reports")
                    .request()
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<>() {
                });
            }
            return null;
        }
    
}
