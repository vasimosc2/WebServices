package dtu.example;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PersonTest {
    PersonService service = new PersonService();
    Person person;

    @When("I call the Person service")
        public void iCallThePersonService() {
            person = service.getPerson();
        }
    @When("Update Person {string} {string}")
        public void updatePerson(String string, String string2) {
            service.setPerson(string,string2);
        }

@Then("I get the answer a person with name {string} address {string}")
public void iGetTheAnswerAPersonWithNameAddress(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    assertEquals(new Person(string,string2),person);
}
}
