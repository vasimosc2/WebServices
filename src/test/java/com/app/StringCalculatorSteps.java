package com.app;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorSteps {

    private StringCalculator stringCalculator;
    private int result;
    private Exception exception;

    @Given("a StringCalculator")
    public void a_string_calculator() {
        stringCalculator = new StringCalculator();
    }

    @When("I add {string}")
    public void i_add(String numbers) {
        try {
            result = stringCalculator.add(numbers);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the result should be {int}")
    public void the_result_should_be(Integer expected) {
        assertEquals(expected, result);
    }

    @Then("an exception with message {string} should be thrown")
    public void an_exception_with_message_should_be_thrown(String expectedMessage) {
        assertNotNull(exception, "An exception was expected but none was thrown.");
        assertEquals(expectedMessage, exception.getMessage());
    }
}
