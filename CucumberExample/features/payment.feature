Feature: Payment
  Scenario: Successful Payment
    Given a customer with name "Vasilis", last name "Moschou", and CPR "1312144"
    Given the customer is registered with the bank with an initial balance of 30 kr
    Then the customer requests 5 tokens
    When the merchant initiates a payment for 50 by the customer
    And the merchant asks for a token from the customer