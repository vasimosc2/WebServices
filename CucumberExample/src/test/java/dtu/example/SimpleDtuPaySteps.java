package dtu.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Token;
import dtu.example.services.CustomerFacadeClient;
import dtu.example.services.MerchantFacadeClient;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleDtuPaySteps {
    
    private Customer customer1,customer2;
    private Merchant merchant;
    private String customerId1, customerId2, merchantId, customerBankAccountId1,customerBankAccountId2, merchantBankAccountId;
    private Token customerToken1, customerToken2;
    private CustomerFacadeClient dtupayCustomerFacade = new CustomerFacadeClient();
    private MerchantFacadeClient dtupayMerchantFacade = new MerchantFacadeClient();

    private BankService bankService = new BankServiceService().getBankServicePort();
    private boolean successful = false;


    CompletableFuture<Boolean> result = new CompletableFuture<>();
    CompletableFuture<Boolean> result2 = new CompletableFuture<>();

    @Given("a customer1 with name {string}, last name {string}, and CPR {string}")
    public void aCustomerWithNameLastNameAndCPR1(String string, String string2, String string3) {
        customer1 = new Customer();
        customer1.setFirstName(string);
        customer1.setLastName(string2);
        customer1.setCprNumber(string3);
        assertNotNull(customer1, "Customer creation failed");
    }

    @And("the customer1 is registered with the bank with an initial balance of {int} kr")
    public void theCustomerIsRegisteredWithTheBankWithAnInitialBalanceOfKr1(int money) throws BankServiceException_Exception {
        User user1 = new User();
        user1.setFirstName(customer1.getFirstName());
        user1.setLastName(customer1.getLastName());
        user1.setCprNumber(customer1.getCprNumber());
        customerBankAccountId1 = bankService.createAccountWithBalance(user1,BigDecimal.valueOf(money));
        customer1.setBankAccount(customerBankAccountId1);
        System.out.println(customerBankAccountId1);
    }


    @And("the customer1 is registered with Simple DTU Pay using their bank account")
    public void theCustomerIsRegisteredWithSimpleDTUPayUsingTheirBankAccount1() {
        assertNotNull(customer1.getCprNumber());
        assertNotNull(customer1.getBankAccount());
        customerId1 = dtupay.register(customer1);
        System.out.println("SANTI customer id: " + customerId1);
    }

    @Then("the customer1 generates {int} tokens")
    public void the_customer_requests_tokens1(int tokenAmount) {
        System.out.println("I am at the tokenRequest");
        boolean isSuccess = dtupay.generateTokens(customerId1, tokenAmount);
        assertTrue(isSuccess);
    }








    @Given("a customer2 with name {string}, last name {string}, and CPR {string}")
    public void aCustomerWithNameLastNameAndCPR2(String string, String string2, String string3) {
        customer2 = new Customer();
        customer2.setFirstName(string);
        customer2.setLastName(string2);
        customer2.setCprNumber(string3);
        assertNotNull(customer2, "Customer creation failed");
    }

    @And("the customer2 is registered with the bank with an initial balance of {int} kr")
    public void theCustomerIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money) throws BankServiceException_Exception {
        User user2 = new User();
        user2.setFirstName(customer2.getFirstName());
        user2.setLastName(customer2.getLastName());
        user2.setCprNumber(customer2.getCprNumber());
        customerBankAccountId2 = bankService.createAccountWithBalance(user2,BigDecimal.valueOf(money));
        customer2.setBankAccount(customerBankAccountId2);
        System.out.println(customerBankAccountId2);
    }


    @And("the customer2 is registered with Simple DTU Pay using their bank account")
    public void theCustomerIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(customer2.getCprNumber());
        assertNotNull(customer2.getBankAccount());
        customerId2 = dtupay.register(customer2);
        System.out.println("SANTI customer id: " + customerId2);
    }

    @Then("the customer2 generates {int} tokens")
    public void the_customer_requests_tokens(int tokenAmount) {
        System.out.println("I am at the tokenRequest");
        boolean isSuccess = dtupay.generateTokens(customerId2, tokenAmount);
        assertTrue(isSuccess);
    }




    @And("Both customers retrieves a token")
    public void BothTakeToken(){
        customerToken1 = dtupay.requestTokenFromCustomer(customerId1);
        customerToken2 = dtupay.requestTokenFromCustomer(customerId2);
        System.out.println("The Id for the first token is :");
        System.out.println(customerToken1.getId());
        System.out.println("The Id for the second token is :");
        System.out.println(customerToken2.getId());
    }


    @When("the merchant initiates a payment for {int} kr {int} for both Clients at the same time")
    public void theMerchantInitiatesAPaymentForKrBoth(int money1 , int money2 ) throws InterruptedException, ExecutionException {
        System.out.println("I am ready to initiate a payment");

        var thread1 = new Thread(() -> {
            result.complete(dtupay.maketransfer(money1, customerToken1.getId(), merchantId));
        });
        var thread2 = new Thread(() -> {
            result2.complete(dtupay.maketransfer(money2, customerToken2.getId(), merchantId));
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        // System.out.println(result.get());
        // System.out.println(result2.get());
        // assertTrue(result.get());
        // assertTrue(result2.get());
    }





    @And("the customer retrieves a token")
    public void the_merchant_asks_for_a_token_from_the_customer() {
        System.out.println("I am ready to get one Token");
        customerToken1 = dtupay.requestTokenFromCustomer(customerId1);
        System.out.println(customerToken1.getId());
        assertNotNull(customerToken1.getId());
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
        merchantId = dtupay.register(merchant);
        System.out.println("SANTI merchant id: " + merchantId);
    }


    @When("the merchant initiates a payment for {int} kr")
    public void theMerchantInitiatesAPaymentForKrGivenTheTokenInPosition(int money ) {
        System.out.println("I am ready to initiate a payment");
        successful = dtupay.maketransfer(money, customerToken1.getId(), merchantId);
        System.out.println(successful);
    }

    @And("the balance of the customer1 at the bank is {int} kr")
    public void checkCustomerBalance(int money) throws BankServiceException_Exception{
        System.out.println(
                "After transaction in step customer1 has " + bankService.getAccount(customer1.getBankAccount()).getBalance()
        );
        System.out.println(
                "After transaction in step customer2 has " + bankService.getAccount(customer2.getBankAccount()).getBalance()
        );
        assertEquals(BigDecimal.valueOf((double) money), bankService.getAccount(customer1.getBankAccount()).getBalance());
    }

    @And("the balance of the customer2 at the bank is {int} kr")
    public void checkCustomerBalance2(int money) throws BankServiceException_Exception{
        assertEquals(BigDecimal.valueOf((double) money), bankService.getAccount(customer2.getBankAccount()).getBalance());
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void checkMerchantBalance(int money) throws BankServiceException_Exception{
        assertEquals(BigDecimal.valueOf((double) money), bankService.getAccount(merchant.getBankAccount()).getBalance());
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
        if (customer1 != null && customer1.getBankAccount() != null) {
            bankService.retireAccount(customer1.getBankAccount());
        }
        if (customer2 != null && customer2.getBankAccount() != null) {
            bankService.retireAccount(customer2.getBankAccount());
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

