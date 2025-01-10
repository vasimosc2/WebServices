package org.acme;

import java.util.List;

import org.acme.models.Payment;
import org.acme.services.PaymentService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/payment")
public class PaymentSerouces {
    PaymentService service = new PaymentService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> Payment() {
        return service.getPayments();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setPayment(Payment payment){
        service.setPayment(payment);
    }
}
