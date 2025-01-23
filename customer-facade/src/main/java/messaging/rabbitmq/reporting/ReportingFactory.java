package messaging.rabbitmq.reporting;
import messaging.EventSender;
import services.ReportingService;


public class ReportingFactory {

    static ReportingService reportingService = null;

    public static ReportingService getService() {
        if (reportingService != null) {
            return reportingService;
        }

        EventSender sender = new ReportingSender();
        reportingService = new ReportingService(sender);
        ReportingListener r = new ReportingListener(reportingService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return reportingService;
    }
}
