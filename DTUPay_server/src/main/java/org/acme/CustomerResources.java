package org.acme;

import java.math.BigDecimal;
import java.util.List;

import org.acme.models.Customer;
import org.acme.services.CustomerService;

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


@Path("/customer")
public class CustomerResources {
    private BankService bankService = new BankServiceService().getBankServicePort();
    CustomerService service = new CustomerService();
    String accountID;
    public record CustInt(Customer customer, int value) {}



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> customer() {
        return service.getCustomers();
    }

    // With this I am trying to put the Customer to the Bank  and then Put him in /customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomerBank(CustInt cInt) throws BankServiceException_Exception{
        Customer customer = cInt.customer ;
        User user = new User();
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());
        user.setCprNumber(customer.getCprNumber());
        accountID = bankService.createAccountWithBalance(user, BigDecimal.valueOf(cInt.value)); 
        customer.setBankAccount(accountID);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void setAngelos() throws BankServiceException_Exception{
        bankService.retireAccount(accountID);
    }
    

    // With this I am trying to put the Customer to SimpleDTUPay with the App
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomerDtu(Customer customer) throws BankServiceException_Exception{
        Account account = bankService.getAccountByCprNumber(customer.getCprNumber());
        if (account != null){
            service.setCustomer(customer);
        }
        
    }
}
