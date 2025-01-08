Feature: Person resource

  Scenario: Retrieve a person
    Given a person exists with name "Susan" and address "USA"
    When I retrieve the person
    Then the person information is updated
