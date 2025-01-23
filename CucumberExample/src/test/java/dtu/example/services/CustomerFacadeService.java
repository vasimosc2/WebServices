package dtu.example.services;

import dtu.example.models.Customer;
import dtu.example.models.Token;
import dtu.example.models.TokenInt;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class CustomerFacadeService {

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
    
    
    
        public Customer getCustomer(String cprNumber){
            Response response = target.path("customers/" + cprNumber).request().get();
            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<>() {
                });
            }
    
            return null;
        }
    
        public Response deleteCustomer(String cprNumber) {
            return target.path("customers/"+ cprNumber).request().delete();
        }

    
    
        public boolean generateTokens(String customerId, int tokenAmount) {
            TokenInt tokenInt = new TokenInt();
            tokenInt.setAmount(tokenAmount);
            tokenInt.setCustomerId(customerId);
            
            System.out.println("I am ready to generate tokens");
    
            // This fails the test it should be pointing into a different Rest
            Response response = target.path("customers/tokens")
                                        .request()
                                        .post(Entity.entity(tokenInt, MediaType.APPLICATION_JSON_TYPE));
    
            return response.getStatus() == Response.Status.OK.getStatusCode();
        }
    
        public Token getUnusedTokenFromCustomer(String customerId) {
            System.out.println("request one unused token from customer");
            Response response = target.path("customers/tokens/" + customerId)
                    .request()
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<>() {
                });
            }
            return null;
        }
    
}
