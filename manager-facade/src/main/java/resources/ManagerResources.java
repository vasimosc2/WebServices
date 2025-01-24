package resources;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import messaging.rabbitmq.reporting.ReportingFactory;
import models.Payment;
import services.ReportingService;


@Path("/reports")
public class ManagerResources{
   


    private final ReportingService reportingService = ReportingFactory.getService();
    
 

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getReport() throws Exception {
        return reportingService.sendRetrieveManagerReportEvent();
    }



}
