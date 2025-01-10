Feature: Person service 
    Scenario: Person service returns correct answer
    When Update Person "Xristina" "France"
    When I call the Person service 
    Then I get the answer a person with name "Xristina" address "France"
   
    Scenario: Update
    When Update Person "Vasilis" "Greece"
    When I call the Person service 
    Then I get the answer a person with name "Vasilis" address "Greece"