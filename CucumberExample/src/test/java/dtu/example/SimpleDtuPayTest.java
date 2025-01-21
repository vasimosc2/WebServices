package dtu.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.models.Token;
import dtu.example.services.SimpleDtuPayService;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.Response;
public class SimpleDtuPayTest {
    
    private Customer customer;
    private Merchant merchant;
    private String customerId, merchantId;
    private Token customerToken;
    private int paymentAmount;
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
        customerId=dtupay.register(customer,money);
        assertNotNull(customerId);
    }

    /*    


    Those are fine are not created for practical reasons for testing

    @And("get Customer with CPR {string}")
    public void getCustomerWithCPR(String cprNumber){
        Customer getCustomer =null;
        getCustomer = dtupay.getCustomer(cprNumber);
        assertNotNull(getCustomer);
    }

    @Then("delete customer with CPR {string}")
    public void deleteCustomer(String cprNumber){
        System.out.println("I am on the retire");
        Response response = dtupay.deleteCustomer(cprNumber);
        assertEquals(200, response.getStatus(), "Expected status code 200, but got " + response.getStatus());
    }






    @Given("a merchant with name {string}, last name {string}, and CPR {string}")
    public void createrMerchant(String firstname,String lastname, String cpr){
        System.out.println("I start the merchant test");
        merchant = new Merchant();
        merchant.setFirstName(firstname);
        merchant.setLastName(lastname);
        merchant.setCprNumber(cpr);
        assertNotNull(merchant, "Merchant creation failed");
    }
    @And("the merchant is registered with the bank with an initial balance of {int} kr")
    public void registerMerchant(int money){
        System.out.println("I Register the Merchant");
        merchantId = dtupay.register(merchant, money);
        assertNotNull(merchantId);
    }

    @And("get Merchant with CPR {string}")
    public void getMerchantrWithCPR(String cprNumber){
        System.out.println("I get the Merchant");
        Merchant getMerchant =null;
        getMerchant = dtupay.getMerchant(cprNumber);
        assertNotNull(getMerchant);
    }
    @Then("delete merchant with CPR {string}")
    public void deleteMerchant(String cprNumber){
        System.out.println("I am on the retire for merchant");
        Response response = dtupay.deleteMerchant(cprNumber);
        assertEquals(200, response.getStatus(), "Expected status code 200, but got " + response.getStatus());
    }


    */

    @Then("the customer requests {int} tokens")
    public void the_customer_requests_tokens(int tokenAmount) {
        System.out.println("I am at the tokenRequest");
        boolean isSuccess = dtupay.requestTokens(customerId, tokenAmount);
        assertTrue(isSuccess);
    }



    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    @And("the merchant asks for a token from the customer")
    public void the_merchant_asks_for_a_token_from_the_customer() {
        System.out.println("I am ready to get one Token");
        customerToken = dtupay.requestTokenFromCustomer(customerId);
        System.out.println(customerToken.getId());
        assertNotNull(customerToken.getId());
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

    @Then("the payment is successful")
    public void sucess(){
        assertTrue(successful);
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void customerBalance(int money) throws BankServiceException_Exception{
        assertEquals(bankService.getAccount(customer.getBankAccount()).getBalance(), BigDecimal.valueOf(money));
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void merchantBalance(int money) throws BankServiceException_Exception{
        assertEquals(bankService.getAccount(merchant.getBankAccount()).getBalance(), BigDecimal.valueOf(money));
    }

    @Then("delete the customer and merchant")
    public void DeleteTests() throws BankServiceException_Exception{
        bankService.retireAccount(customer.getBankAccount());
        bankService.retireAccount(merchant.getBankAccount());
    }
    */
}
