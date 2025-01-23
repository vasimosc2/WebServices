// package resources;

// import java.util.List;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.MediaType;
// import messaging.rabbitmq.payment.PaymentFactory;
// import models.PaymentManager;
// import services.PaymentService;

// @Path("/management")
// public class ManagmentResources {

//     private final PaymentService service= PaymentFactory.getService();

//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public List<PaymentManager> getAllPayments() throws Exception {
//         return service.getAllPayments();
//     }
// }
