package org.acme;
import java.math.BigDecimal;

import org.acme.models.Customer;
import org.acme.models.Merchant;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;

@Path("/bank")
public class BankResources {
    private BankService bankService = new BankServiceService().getBankServicePort();
    Account account; 

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BigDecimal Money() throws BankServiceException_Exception{
        return bankService.getAccount(this.account.getId()).getBalance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void setAmount(Customer customer) throws BankServiceException_Exception{
        account = bankService.getAccountByCprNumber(customer.getCprNumber());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void setAmount(Merchant merchant) throws BankServiceException_Exception{
        account = bankService.getAccountByCprNumber(merchant.getCprNumber());
    }
}
