package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.MoneyTransferredObject;
import models.Payment;
import services.ReportService;

import java.math.BigDecimal;
import java.util.List;

public class ReportingSteps {

    ReportService reportService = new ReportService();
    List<Payment> listPaymentsMAnager;

    @Given("a list of transactions saved in the repository")
    public void aListOfTransactionsSavedInTheRepository() {

        MoneyTransferredObject moneyTransferredObject1 = new MoneyTransferredObject("token1", "CUST-1", "MERC-1", BigDecimal.valueOf(2.2));

        MoneyTransferredObject moneyTransferredObject2 = new MoneyTransferredObject("token2", "CUST-2", "MERC-2", BigDecimal.valueOf(33.3));

        MoneyTransferredObject moneyTransferredObject3 = new MoneyTransferredObject("token3", "CUST-3", "MERC-3", BigDecimal.valueOf(444.4));

        MoneyTransferredObject moneyTransferredObject4 = new MoneyTransferredObject("token4", "CUST-4", "MERC-4", BigDecimal.valueOf(5555.5));

        reportService.addMoneyTransferredToRepos(moneyTransferredObject1);
        reportService.addMoneyTransferredToRepos(moneyTransferredObject2);
        reportService.addMoneyTransferredToRepos(moneyTransferredObject3);
        reportService.addMoneyTransferredToRepos(moneyTransferredObject4);

    }

    @When("a request to see all transactions is made by the manager")
    public void aRequestToSeeAllTransactionsIsMadeByTheManager() {

        listPaymentsMAnager = reportService.getManagerPaymentReport();
    }

    @Then("the list of transactions is shown")
    public void theListOfTransactionsIsShown() {
        System.out.println("listPaymentsMAnager.size() = " + listPaymentsMAnager.size());

        for ( Payment payment : listPaymentsMAnager) {
            System.out.println("payment = " + payment.getCustomerId() + "," + payment.getTokenId() + "," + payment.getMerchantId() + "," + payment.getAmount());
        }

        assert listPaymentsMAnager.size() == 4;
    }

}
