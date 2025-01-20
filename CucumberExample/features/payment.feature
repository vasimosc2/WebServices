Feature: Payment
  Scenario: Successful Payment
    Given a customer with name "Vasilis", last name "Moschou", and CPR "1312144"
    Given the customer is registered with the bank with an initial balance of 450 kr
    Given a customer with name "Xristina", last name "Vagena", and CPR "XXX"
    Given the customer is registered with the bank with an initial balance of 300 kr
    And get Customer with CPR "1312144"
    Then delete customer with CPR "XXX"
    Given a merchant with name "Aggelos", last name "Michelis", and CPR "133333"
    And the merchant is registered with the bank with an initial balance of 1000 kr
    Given a merchant with name "Aggelos", last name "Michelis", and CPR "999"
    And the merchant is registered with the bank with an initial balance of 1000 kr
    And get Merchant with CPR "133333"
    Then delete merchant with CPR "999"