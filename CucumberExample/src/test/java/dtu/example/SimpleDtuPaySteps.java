package dtu.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Token;
import dtu.example.services.CustomerFacadeService;
import dtu.example.services.MerchantFacadeService;
// import dtu.example.services.SimpleDtuPayService;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

public class SimpleDtuPaySteps {
    
    private Customer customer;
    private Merchant merchant;
    private String customerId, merchantId, customerBankAccountId, merchantBankAccountId;
    private Token customerToken;
    // private int paymentAmount;
    // private SimpleDtuPayService dtupay = new SimpleDtuPayService();

    private CustomerFacadeService dtupayCustomerFacade = new CustomerFacadeService();
    private MerchantFacadeService dtupayMerchantFacade = new MerchantFacadeService();

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

    @And("the customer is registered with the bank with an initial balance of {int} kr")
    public void theCustomerIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money) throws BankServiceException_Exception {
        User user = new User();
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());
        user.setCprNumber(customer.getCprNumber());
        customerBankAccountId = bankService.createAccountWithBalance(user,BigDecimal.valueOf(money));
        customer.setBankAccount(customerBankAccountId);
    }


    @And("the customer is registered with Simple DTU Pay using their bank account")
    public void theCustomerIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(customer.getCprNumber()); //maybe not needed
        assertNotNull(customer.getBankAccount()); //maybe not needed
        customerId = dtupayCustomerFacade.register(customer);
        System.out.println("SANTI customer id: " + customerId);
    }

    @Then("the customer generates {int} tokens")
    public void the_customer_requests_tokens(int tokenAmount) {
        System.out.println("I am at the tokenRequest");
        boolean isSuccess = dtupayCustomerFacade.generateTokens(customerId, tokenAmount);
        assertTrue(isSuccess);
    }

    @And("the customer retrieves a token")
    public void the_merchant_asks_for_a_token_from_the_customer() {
        System.out.println("I am ready to get one Token");
        customerToken = dtupayCustomerFacade.requestTokenFromCustomer(customerId);
        System.out.println(customerToken.getId());
        assertNotNull(customerToken.getId());
    }
     
     
    @Given("a merchant with name {string}, last name {string}, and CPR {string}")
    public void createrMerchant(String firstname,String lastname, String cpr){
        merchant = new Merchant();
        merchant.setFirstName(firstname);
        merchant.setLastName(lastname);
        merchant.setCprNumber(cpr);
        assertNotNull(merchant, "Merchant creation failed");
    }

    @And("the merchant is registered with the bank with an initial balance of {int} kr")
    public void registerMerchant(int money) throws BankServiceException_Exception {
        User user = new User();
        user.setFirstName(merchant.getFirstName());
        user.setLastName(merchant.getLastName());
        user.setCprNumber(merchant.getCprNumber());
        merchantBankAccountId = bankService.createAccountWithBalance(user,BigDecimal.valueOf(money));
        merchant.setBankAccount(merchantBankAccountId);
    }

    @And("the merchant is registered with Simple DTU Pay using their bank account")
    public void theMerchantIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(merchant.getCprNumber()); 
        assertNotNull(merchant.getBankAccount());
        merchantId = dtupayMerchantFacade.register(merchant);
        System.out.println("SANTI merchant id: " + merchantId);
    }


    @When("the merchant initiates a payment for {int} kr")
    public void theMerchantInitiatesAPaymentForKrGivenTheTokenInPosition(int money ) {
        System.out.println("I am ready to initiate a payment");
        successful = dtupayMerchantFacade.maketransfer(money, customerToken.getId(), merchantId);

    }

    @Then("the payment is successful")
    public void sucess(){
        assertTrue(successful);
    }

    /* 

    @And("the balance of the customer at the bank is {int} kr")
    public void customerBalance(int money) throws BankServiceException_Exception{
        assertEquals(bankService.getAccount(customer.getBankAccount()).getBalance(), BigDecimal.valueOf(money));
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void merchantBalance(int money) throws BankServiceException_Exception{
        assertEquals(bankService.getAccount(merchant.getBankAccount()).getBalance(), BigDecimal.valueOf(money));
    }

    */

    @After
    public void cleanupBankAccounts() throws BankServiceException_Exception {
        if (customer != null && customer.getBankAccount() != null) {
            bankService.retireAccount(customer.getBankAccount());
        }
        if (merchant != null && merchant.getBankAccount() != null) {
            bankService.retireAccount(merchant.getBankAccount());
        }
    }

    
    

    /* 
    @Given("the customer is registered with Simple DTU Pay using their bank account")
    public void theCustomerIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() throws BankServiceException_Exception {
        assertNotNull(customer.getCprNumber());
        Account account = bankService.getAccount(customer.getBankAccount());
        dtupay.register(account.getUser(),account.getId(),"customer");
       
    }


    @Given("a merchant with name {string}, last name {string}, and CPR {string}")
    public void aMerchantWithNameLastNameAndCPR(String string, String string2, String string3) {
        merchant = new Merchant();
        merchant.setFirstName(string);
        merchant.setLastName(string2);
        merchant.setCprNumber(string3);
        assertNotNull(merchant, "Customer creation failed");

    }

    @Given("the merchant is registered with the bank with an initial balance of {int} kr")
    public void theMerchantIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money) throws BankServiceException_Exception {
        User user = new User();
        user.setFirstName(merchant.getFirstName());
        user.setLastName(merchant.getLastName());
        user.setCprNumber(merchant.getCprNumber());
        merchantAccountId = bankService.createAccountWithBalance(user,BigDecimal.valueOf(money));
        merchant.setBankAccount(merchantAccountId);
    }


    @Given("the merchant is registered with Simple DTU Pay using their bank account")
    public void theMerchantIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() throws BankServiceException_Exception {
        assertNotNull(merchant.getCprNumber());
        Account account = bankService.getAccount(merchant.getBankAccount());
        dtupay.register(account.getUser(),account.getId(),"merchant");
    }
    
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void TransferMoney(int money){
        successful = dtupay.maketransfer(money,customer,merchant);
    }



    @Then("delete the customer and merchant")
    public void DeleteTests() throws BankServiceException_Exception{
        bankService.retireAccount(customer.getBankAccount());
        bankService.retireAccount(merchant.getBankAccount());
    }
    */
}
