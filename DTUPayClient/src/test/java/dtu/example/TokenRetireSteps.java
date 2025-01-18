package dtu.example;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import dtu.example.models.Token;
import dtu.example.services.SimpleDtuPayService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.Response;

public class TokenRetireSteps {
    
    private String customerId;
    private List<Token> allTokens;
    private Token currentToken;
    

    private SimpleDtuPayService dtupay = new SimpleDtuPayService();


    @Given("a user with customerId {string}")
    public void a_user_with_customerId(String cid) {
        this.customerId = cid;
    }

    @When("the user requests {int} token")
    public void the_user_requests_n_token(int count) {
        dtupay.generateTokens(this.customerId, count);
    }

    @When("the user retrieves the list of all tokens")
    public void the_user_retrieves_the_list_of_all_tokens() {
        this.allTokens = dtupay.getAllTokens();
    }

    @Then("user {string} should have exactly {int} unused token")
    public void user_should_have_exactly_n_unused_token(String expectedCustomerId, int expectedCount) {
        long count = this.allTokens.stream()
            .filter(t -> Objects.equals(t.getCustomerId(), expectedCustomerId))
            .filter(t -> !t.isUsed())
            .count();
        assertEquals(expectedCount, count);
    }

    @When("the user validates that token")
    public void the_user_validates_that_token() {
        this.currentToken = this.allTokens.stream()
            .filter(t -> t.getCustomerId().equals(this.customerId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No token found for " + this.customerId));

        dtupay.validateToken(currentToken.getTokenId());
    }

    @When("the user retires that token")
    public void the_user_retires_that_token() {
        dtupay.retireToken(currentToken.getTokenId());
    }


}
