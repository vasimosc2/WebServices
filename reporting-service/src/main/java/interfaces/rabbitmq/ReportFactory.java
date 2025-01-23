package interfaces.rabbitmq;

import messaging.EventSender;
import services.ReportEventService;


public class ReportFactory {

    static ReportEventService reportEventService = null;

    public ReportEventService getService() {

        if (reportEventService != null) {
            return reportEventService;
        }

        
        EventSender b = new ReportSender();
        reportEventService = new ReportEventService(b, new ReportService());
        ReportListener r = new ReportListener(reportEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return reportEventService;
    }
}
