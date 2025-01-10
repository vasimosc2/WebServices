package dtu.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.example.models.Customer;
import dtu.example.models.Merchant;
import dtu.example.services.SimpleDtuPayService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class SimpleDtuPayTest {
    
    private Customer customer;
    private Merchant merchant;
    private String customerId, merchantId;
    private SimpleDtuPayService dtupay = new SimpleDtuPayService();
    private boolean successful = false;

    @Given("a customer with name {string}")
    public void aCustomerWithName(String name) {
        customer = new Customer(name);
        customer.setId();
        assertNotNull(customer, "Customer creation failed");
    }

    @Given("the customer is registered with Simple DTU Pay")
    public void theCustomerIsRegisteredWithSimpleDTUPay() {
        customerId = dtupay.register(customer);
        assertNotNull(customerId, "Customer registration failed");
    }

    @Given("a merchant with name {string}")
    public void aMerchantWithName(String name) {
        merchant = new Merchant(name);
        merchant.setId();
        assertNotNull(merchant, "Merchant creation failed");
    }

    @Given("the merchant is registered with Simple DTU Pay")
    public void theMerchantIsRegisteredWithSimpleDTUPay() {
        merchantId = dtupay.register(merchant);
        assertNotNull(merchantId, "Merchant registration failed");
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(Integer amount) {
        successful = dtupay.pay(amount, customerId, merchantId);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful, "Payment was not successful");
    }
    
}
