package dtu.example;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.services.SimpleDtuPayService;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class SimpleDtuPaySteps {
    
    private Customer customer;
    private Merchant merchant;
    private String customerId, merchantId, customerBankAccountId, merchantBankAccountId;
    private SimpleDtuPayService dtupay = new SimpleDtuPayService();


    private BankService bankService = new BankServiceService().getBankServicePort();
    private boolean successful = false;

    @Given("a customer with name {string}, last name {string}, and CPR {string}")
    public void aCustomerWithNameLastNameAndCPR(String string, String string2, String string3) {
        customer = new Customer();
        customer.setFirstName(string);
        customer.setLastName(string2);
        customer.setCprNumber(string3);
        assertNotNull(customer, "Customer creation failed");

    }
       
    @Given("the customer is registered with the bank with an initial balance of {int} kr")
    public void theCustomerIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money) throws BankServiceException_Exception {
        User user = new User();
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());
        user.setCprNumber(customer.getCprNumber());
        customerBankAccountId = bankService.createAccountWithBalance(user,BigDecimal.valueOf(money));
        customer.setBankAccount(customerBankAccountId);
    }

    @Given("the customer is registered with Simple DTU Pay using their bank account")
    public void theCustomerIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(customer.getCprNumber()); //maybe not needed
        customerId = dtupay.registerCustomer(customer);
        customer.setId(customerId);
        System.out.println("SANTI customerId: " + customerId);
    }


    @Given("a merchant with name {string}, last name {string}, and CPR {string}")
    public void aMerchantWithNameLastNameAndCPR(String string, String string2, String string3) {
        merchant = new Merchant();
        merchant.setFirstName(string);
        merchant.setLastName(string2);
        merchant.setCprNumber(string3);
        assertNotNull(merchant, "Merchant creation failed");

    }

    @Given("the merchant is registered with the bank with an initial balance of {int} kr")
    public void theMerchantIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money) throws BankServiceException_Exception {
        User user = new User();
        user.setFirstName(merchant.getFirstName());
        user.setLastName(merchant.getLastName());
        user.setCprNumber(merchant.getCprNumber());
        merchantBankAccountId = bankService.createAccountWithBalance(user,BigDecimal.valueOf(money));
        merchant.setBankAccount(merchantBankAccountId);
    }


    @Given("the merchant is registered with Simple DTU Pay using their bank account")
    public void theMerchantIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(merchant.getCprNumber()); //maybe not needed

        merchantId = dtupay.registerMerchant(merchant);
        merchant.setId(merchantId);
        System.out.println("SANTI merchantId: " + merchantId);
    }
    
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void TransferMoney(int money){
        successful = dtupay.makeTransfer(money, customer.getId(), merchant.getId());
    }

    @Then("the payment is successful")
    public void success(){
        assertTrue(successful);
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void customerBalance(int money) throws BankServiceException_Exception{
        assertEquals(BigDecimal.valueOf(money), bankService.getAccount(customer.getBankAccount()).getBalance());
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void merchantBalance(int money) throws BankServiceException_Exception{
        assertEquals(BigDecimal.valueOf(money), bankService.getAccount(merchant.getBankAccount()).getBalance());
    }

//    @Then("delete the customer and merchant")
//    public void DeleteTests() throws BankServiceException_Exception{
//        bankService.retireAccount(customer.getBankAccount());
//        bankService.retireAccount(merchant.getBankAccount());
//    }

    @After
    public void cleanupBankAccounts() throws BankServiceException_Exception {
        bankService.retireAccount(customer.getBankAccount());
        bankService.retireAccount(merchant.getBankAccount());
    }

}
