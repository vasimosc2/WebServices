Feature: Payment
  Scenario: Successful Payment with Token
    Given a customer with name "Vasilis", last name "Moschou", and CPR "CPRNUM16"
    And the customer is registered with the bank with an initial balance of 1000 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer asks for 3 new tokens
    And a merchant with name "Susan", last name "Pounaxero", and CPR "CPRNUM26"
    And the merchant is registered with the bank with an initial balance of 1000 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    When the merchant initiates a payment for 15 kr given the token in position 2
    Then the payment is successful
    And the balance of the customer at the bank is 985 kr
    And the balance of the merchant at the bank is 1015 kr