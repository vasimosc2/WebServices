Feature: Payment
  Scenario: Successful Payment
    Given a customer with name "Vasilis", last name "Moschou", and CPR "1312144"
    And the customer is registered with the bank with an initial balance of 450 kr
    