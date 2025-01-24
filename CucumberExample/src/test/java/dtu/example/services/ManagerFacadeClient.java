/**
 * @primary-author Marcu Muro (s233662)
 * @co-author Kaizhi Fan (s240047)
 *
 *
 */

package dtu.example.services;

import dtu.example.models.Payment;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
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
