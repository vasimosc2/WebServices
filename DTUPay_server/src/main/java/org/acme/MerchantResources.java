package org.acme;

import java.math.BigDecimal;
import java.util.List;

import org.acme.models.Merchant;
import org.acme.services.MerchantService;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


    
    
@Path("/merchant")
public class MerchantResources{
    private BankService bankService = new BankServiceService().getBankServicePort();
    MerchantService service = new MerchantService();
    String accountID;
    public record MerchInt(Merchant merchant, int value) {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> customer() {
        return service.getMerchants();
    }

    // With this I am trying to put the Customer to the Bank  and then Put him in /customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void setMerchantBank(MerchInt merchInt) throws BankServiceException_Exception{
        User user = new User();
        user.setFirstName(merchInt.merchant.getFirstName());
        user.setLastName(merchInt.merchant.getLastName());
        user.setCprNumber(merchInt.merchant.getCprNumber());
        accountID = bankService.createAccountWithBalance(user, BigDecimal.valueOf(merchInt.value)); 
        merchInt.merchant.setBankAccount(accountID);
        
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void setAngelos() throws BankServiceException_Exception{
        bankService.retireAccount(accountID);
    }

    // With this I am trying to put the Customer to SimpleDTUPay with the App
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomerBank(Merchant merchant) throws BankServiceException_Exception{
        Account account = bankService.getAccountByCprNumber(merchant.getCprNumber());
        if (account != null){
            service.setMerchant(merchant);
        }
        
    }
  
}
