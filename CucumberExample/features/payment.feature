Feature: Payment
  Scenario: Successful Payment
    Given a customer with name "Vasilis", last name "Moschou", and CPR "CPRNUM17512"
    And the customer is registered with the bank with an initial balance of 1000 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 5 tokens
    
    And the customer retrieves a token
    