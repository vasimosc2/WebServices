package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.MoneyTransferredObject;
import models.Payment;
import models.PaymentCustomer;
import models.PaymentMerchant;
import services.ReportService;

import java.math.BigDecimal;
import java.util.List;

public class ReportingSteps {

    ReportService reportService = new ReportService();
    List<Payment> listPaymentsManager;
    List<PaymentCustomer> listPaymentsCustomer;
    List<PaymentMerchant> listPaymentsMerchant;


    @Given("a list of transactions saved in the repository")
    public void aListOfTransactionsSavedInTheRepository() {
        MoneyTransferredObject moneyTransferredObject1 = new MoneyTransferredObject("token1", "CUST-1", "MERC-1", BigDecimal.valueOf(2.2));

        MoneyTransferredObject moneyTransferredObject2 = new MoneyTransferredObject("token2", "CUST-1", "MERC-2", BigDecimal.valueOf(33.3));

        MoneyTransferredObject moneyTransferredObject3 = new MoneyTransferredObject("token3", "CUST-3", "MERC-3", BigDecimal.valueOf(444.4));

        MoneyTransferredObject moneyTransferredObject4 = new MoneyTransferredObject("token4", "CUST-4", "MERC-4", BigDecimal.valueOf(5555.5));

        reportService.addMoneyTransferredToRepos(moneyTransferredObject1);
        reportService.addMoneyTransferredToRepos(moneyTransferredObject2);
        reportService.addMoneyTransferredToRepos(moneyTransferredObject3);
        reportService.addMoneyTransferredToRepos(moneyTransferredObject4);

    }

    @When("a request to see all transactions is made by the manager")
    public void aRequestToSeeAllTransactionsIsMadeByTheManager() {

        listPaymentsManager = reportService.getManagerPaymentReport();
    }

    @Then("the list of transactions is shown")
    public void theListOfTransactionsIsShown() {
        System.out.println("listPaymentsMAnager.size() = " + listPaymentsManager.size());

        for ( Payment payment : listPaymentsManager) {
            System.out.println("SANTI payment manager = " + payment.getCustomerId() + "," + payment.getTokenId() + "," + payment.getMerchantId() + "," + payment.getAmount());
        }

        assert listPaymentsManager.size() == 4;

        reportService.clear();
    }

    @When("a request to see all transactions is made by the customer {string}")
    public void aRequestToSeeAllTransactionsIsMadeByTheCustomer(String arg0) {
        listPaymentsCustomer = reportService.getCustomerPaymentReport(arg0);
    }

    @Then("the list of transactions for the customer is shown")
    public void theListOfTransactionsForTheCustomerIsShown() {
        System.out.println("listPaymentsCustomer.size() = " + listPaymentsCustomer.size());

        for ( PaymentCustomer payment : listPaymentsCustomer) {
            System.out.println("SANTI payment customer = " + payment.getTokenId() + "," + payment.getMerchantId() + "," + payment.getAmount());
        }

        assert listPaymentsCustomer.size() == 2;

        reportService.clear();

    }

    @Then("the list of transactions for the merchant is shown")
    public void theListOfTransactionsForTheMerchantIsShown() {
        System.out.println("listPaymentsMerchant.size() = " + listPaymentsMerchant.size());

        for ( PaymentMerchant payment : listPaymentsMerchant) {
            System.out.println("SANTI payment merchant = " + payment.getTokenId() +  "," + payment.getAmount());
        }

        assert listPaymentsMerchant.size() == 1;
        reportService.clear();
    }

    @When("a request to see all transactions is made by the merchant {string}")
    public void aRequestToSeeAllTransactionsIsMadeByTheMerchant(String arg0) {
        listPaymentsMerchant = reportService.getMerchantPaymentReport(arg0);
    }
}
