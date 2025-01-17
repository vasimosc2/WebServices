package org.acme.resources;

// import java.math.BigDecimal;
import java.util.List;

import jakarta.inject.Inject;
import org.acme.models.BankPay;
// import org.acme.models.Customer;
// import org.acme.models.Merchant;
import org.acme.models.Payment;
import org.acme.services.PaymentService;

// import dtu.ws.fastmoney.Account;
// import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
// import dtu.ws.fastmoney.BankServiceService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/payments")
public class PaymentResources{
    @Inject
    PaymentService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> Payment() {
        return service.getPayments();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void setBankPayment(BankPay bankPay) throws BankServiceException_Exception{
        service.setPayment(bankPay);
    }
}
