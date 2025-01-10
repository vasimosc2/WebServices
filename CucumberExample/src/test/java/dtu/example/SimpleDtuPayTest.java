package dtu.example;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.services.SimpleDtuPayService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class SimpleDtuPayTest {
    
    private Customer customer;
    private Merchant merchant;
    private String customerId, merchantId;
    private SimpleDtuPayService dtupay = new SimpleDtuPayService();
    private boolean successful = false;

    @Given("a customer with name {string}, last name {string}, and CPR {string}")
    public void aCustomerWithNameLastNameAndCPR(String string, String string2, String string3) {
        customer = new Customer();
        customer.setFirstName(string);
        customer.setLastName(string2);
        customer.setCprNumber(string3);
        customer.setId();
        assertNotNull(customer, "Customer creation failed");

    }
       
    @Given("the customer is registered with the bank with an initial balance of {int} kr")
    public void theCustomerIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money) {
        dtupay.registerBank(customer,money);
    }

    @Given("the customer is registered with Simple DTU Pay using their bank account")
    public void theCustomerIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(customer.getCprNumber());
        customerId = dtupay.register(customer);
       
    }


    @Given("a merchant with name {string}, last name {string}, and CPR {string}")
    public void aMerchantWithNameLastNameAndCPR(String string, String string2, String string3) {
        merchant = new Merchant();
        merchant.setFirstName(string);
        merchant.setLastName(string2);
        merchant.setCprNumber(string3);
        merchant.setId();
        assertNotNull(merchant, "Customer creation failed");

    }

    @Given("the merchant is registered with the bank with an initial balance of {int} kr")
    public void theMerchantIsRegisteredWithTheBankWithAnInitialBalanceOfKr(Integer int1) {
        dtupay.registerBank(merchant,int1);
    }


    @Given("the merchant is registered with Simple DTU Pay using their bank account")
    public void theMerchantIsRegisteredWithSimpleDTUPayUsingTheirBankAccount() {
        assertNotNull(merchant.getCprNumber());
        merchantId = dtupay.register(merchant);
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
    public void customerBalance(int money){
        assertEquals(dtupay.Money(customer), BigDecimal.valueOf(money));
    }
    @And("the balance of the merchant at the bank is {int} kr")
    public void merchantBalance(int money){
        assertEquals(dtupay.Money(merchant), BigDecimal.valueOf(money));
    }
}
