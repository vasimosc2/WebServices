import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.EventSender;
import models.MoneyTransferredObject;
import services.ReportEventService;
import services.ReportService;

import java.math.BigDecimal;

import static utils.EventTypes.CUSTOMER_REPORTS_GENERATED;
import static utils.EventTypes.MONEY_TRANSFERRED;

public class ReportingSteps {

    ReportService reportService = new ReportService();
    EventSender eventSender;
    ReportEventService reportEventService = new ReportEventService(eventSender, reportService);





    @Given("transactions saved in the repository")
    public void transactionsSavedInTheRepository() throws Exception {
        MoneyTransferredObject moneyTransferredObject1 = new MoneyTransferredObject("token1", "CUST-1", "MERC-1", BigDecimal.valueOf(2.2));
        Event eventOut1 = new Event(MONEY_TRANSFERRED, new Object[]{moneyTransferredObject1});
        eventSender.sendEvent(eventOut1);

        MoneyTransferredObject moneyTransferredObject2 = new MoneyTransferredObject("token2", "CUST-2", "MERC-2", BigDecimal.valueOf(33.3));
        Event eventOut2 = new Event(MONEY_TRANSFERRED, new Object[]{moneyTransferredObject2});
        eventSender.sendEvent(eventOut2);

        MoneyTransferredObject moneyTransferredObject3 = new MoneyTransferredObject("token3", "CUST-3", "MERC-3", BigDecimal.valueOf(444.4));
        Event eventOut3 = new Event(MONEY_TRANSFERRED, new Object[]{moneyTransferredObject3});
        eventSender.sendEvent(eventOut3);

        MoneyTransferredObject moneyTransferredObject4 = new MoneyTransferredObject("token3", "CUST-4", "MERC-4", BigDecimal.valueOf(5555.5));
        Event eventOut4 = new Event(MONEY_TRANSFERRED, new Object[]{moneyTransferredObject4});
        eventSender.sendEvent(eventOut3);

    }

    @When("a request to see all transactions is made by the manager")
    public void aRequestToSeeAllTransactionsIsMadeByTheManager() {

    }

    @Then("the list of transactions is shown")
    public void theListOfTransactionsIsShown() {
    }
}
