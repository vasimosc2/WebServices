package dtu.example.services;

import java.math.BigDecimal;

import dtu.example.models.BankPay;
import dtu.example.models.Merchant;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class MerchantFacadeClient {

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8086/");

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

        public Merchant getMerchant(String cprNumber){
            Response response = target.path("merchants/" + cprNumber).request().get();
            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<>() {
                });
            }
    
            return null;
        }
    
        public Response deleteMerchant(String cprNumber) {
            return target.path("merchants/"+ cprNumber).request().delete();
        }

    
    
        public boolean maketransfer(int moneyint, String tokenId, String merchantId) {
            BigDecimal money = BigDecimal.valueOf(moneyint);
            Response response = target.path("merchants/payments")
                    .request()
                    .post(Entity.entity(new BankPay(money, tokenId, merchantId), MediaType.APPLICATION_JSON));

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            return false;
        }
        return true;
    }




   
    
}
