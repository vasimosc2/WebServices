package dtu.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dtu.example.services.HelloService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class HelloServerTest {
    String result;
    HelloService service = new HelloService();
    @When("I call the hello service")
    public void icallhello(){
        result = service.hello();
    }
    @Then("I get the answer {string}")
    public void iGetTheAnswer(String string) { 
        assertEquals(string, result); 
    }
}
