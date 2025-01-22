Feature: Payment
  Scenario: Successful Payment
    Given a customer with name "Vasilis", last name "Moschou", and CPR "qpoiweurqwerpiu2"
    And the customer is registered with the bank with an initial balance of 1000 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 5 tokens
    Given a merchant with name "Jan", last name "fucker", and CPR "Millioner"
    And the merchant is registered with the bank with an initial balance of 5 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant initiates a payment for 3 kr
    Then the payment is successful