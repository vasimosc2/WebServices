package dtu.example.services;

import dtu.example.models.Customer;
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


public class CustomerFacadeClient {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8087/");
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

    
    
        public boolean generateTokens(String customerId, int tokenAmount) {
            
            System.out.println("I am ready to generate tokens");
    
            // This fails the test it should be pointing into a different Rest
            Response response = target.path("customers/" + customerId + "/tokens")
                                        .request()
                                        .post(Entity.entity(tokenAmount, MediaType.APPLICATION_JSON_TYPE));
    
            return response.getStatus() == Response.Status.OK.getStatusCode();
        }
    
        public Token getUnusedTokenFromCustomer(String customerId) {
            System.out.println("request one unused token from customer");
            Response response = target.path("customers/" + customerId + "/tokens")
                    .request()
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<>() {
                });
            }
            return null;
        }

        public List<PaymentCustomer> getCustomerPaymentReport(String customerId) {
            System.out.println("request payment report for customer");
            Response response = target.path("customers/" + customerId + "/reports")
                    .request()
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<>() {
                });
            }
            return null;
        }
    
}
