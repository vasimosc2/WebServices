Feature: Payment
  Scenario: Successful Payment
    Given a customer with name "Vasilis", last name "Moschou", and CPR "gfdsfdfdfgdgdfsgds"
    And the customer is registered with the bank with an initial balance of 1000 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 5 tokens
    Given a merchant with name "Jan", last name "ariel", and CPR "gfdkgjfdfdfdfdfklsj"
    And the merchant is registered with the bank with an initial balance of 5 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant initiates a payment for 20 kr
    And the balance of the customer at the bank is 980 kr
    And the balance of the merchant at the bank is 25 kr
    Then the payment is successful