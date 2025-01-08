package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.MediaType;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class PersonResourceTest {

    @Given("a person exists with name {string} and address {string}")
    public void a_person_exists_with_name_and_address(String name, String address) {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Person(name, address))
                .when()
                .put("http://localhost:8081/person");
    }

    @When("I retrieve the person")
    public void i_retrieve_the_person() {
        RestAssured.when()
                .get("http://localhost:8081/person")
                .then()
                .statusCode(200)
                .body("name", equalTo("Susan"))
                .body("address", equalTo("USA"));
    }

    @Then("the person information is updated")
    public void the_person_information_is_updated() {
        Person updatedPerson = new Person("John", "Canada");
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedPerson)
                .when()
                .put("http://localhost:8081/person");

        RestAssured.when()
                .get("http://localhost:8081/person")
                .then()
                .statusCode(200)
                .body("name", equalTo("John"))
                .body("address", equalTo("Canada"));
    }
}
