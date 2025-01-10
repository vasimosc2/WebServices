package org.acme;

import java.math.BigDecimal;
import java.util.List;

import org.acme.models.Customer;
import org.acme.models.Merchant;
import org.acme.models.Payment;
import org.acme.services.PaymentService;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/payment")
public class PaymentResources{
    PaymentService service = new PaymentService();

    private BankService bankService = new BankServiceService().getBankServicePort();

    public record BankPay(int money,Customer customer, Merchant merchant) {}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> Payment() {
        return service.getPayments();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void setBankPayment(BankPay bankPay) throws BankServiceException_Exception{
        Customer customer = bankPay.customer;
        Merchant merchant = bankPay.merchant;
        int money = bankPay.money;

        Account customerAccount = bankService.getAccountByCprNumber(customer.getCprNumber());
        Account merchantAccount = bankService.getAccountByCprNumber(merchant.getCprNumber());

        bankService.transferMoneyFromTo(customerAccount.getId(), merchantAccount.getId(), BigDecimal.valueOf(money), "Random Reason");
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setPayment(Payment payment){
        service.setPayment(payment);
    }
}
