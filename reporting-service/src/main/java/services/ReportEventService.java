/**
 * @primary-author Marco Muro (s233662)
 * @co-author Angelos Michelis (s232488)
 *
 */
package services;
import com.google.gson.Gson;


import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.*;
import services.interfaces.IReportService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.EventTypes.*;

public class ReportEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(ReportEventService.class.getName());

    private final EventSender eventSender;
    
    private final IReportService service;
    private final Gson gson = new Gson();

    public ReportEventService(EventSender eventSender, ReportService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case MONEY_TRANSFERRED:
                System.out.println("Hello from MoneyTransferred Event");
                MoneyTransferredObject moneyTransferredObject = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), MoneyTransferredObject.class);
                service.addMoneyTransferredToRepos(moneyTransferredObject);
                break;
            case CUSTOMER_REPORTS_REQUESTED:
                System.out.println("Hello from CUSTOMER_REPORTS_REQUESTED");
                String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                List<PaymentCustomer> customerPaymentsReport = service.getCustomerPaymentReport(customerId);
                for (PaymentCustomer paymentCustomer : customerPaymentsReport) {
                    System.out.println("SANTI: " + paymentCustomer.getTokenId());
                    System.out.println("SANTI: " + paymentCustomer.getMerchantId());
                    System.out.println("SANTI: " + paymentCustomer.getAmount());
                }
                Event eventOut = new Event(CUSTOMER_REPORTS_GENERATED, new Object[]{customerPaymentsReport});
                eventSender.sendEvent(eventOut);
                break;
            case MERCHANT_REPORTS_REQUESTED:
                System.out.println("Hello from MERCHANT_REPORTS_REQUESTED");
                String merchantId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                List<PaymentMerchant> merchantPaymentsReport = service.getMerchantPaymentReport(merchantId);
                Event eventOut2 = new Event(MERCHANT_REPORTS_GENERATED, new Object[]{merchantPaymentsReport});
                eventSender.sendEvent(eventOut2);
                break;
            case MANAGER_REPORTS_REQUESTED:
                System.out.println("Hello from ManagerReportsRequested Event");
                List<Payment> managerPaymentsReport = service.getManagerPaymentReport();
                Event eventOut3 = new Event(MANAGER_REPORTS_GENERATED, new Object[]{managerPaymentsReport});
                eventSender.sendEvent(eventOut3);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
