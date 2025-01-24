package resources;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import messaging.rabbitmq.reporting.ReportingFactory;
import models.BankPay;
import models.Merchant;
import models.PaymentManager;
import services.ReportingService;


@Path("/managerment")
public class ManagerResources{
   


    private final ReportingService reportingService = ReportingFactory.getService();
    
 

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PaymentManager> getReport() throws Exception {
        return reportingService.sendRetrieveManagerReportEvent();
    }



}
