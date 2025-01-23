Feature: Payment
  Scenario: Successful Payment
    Given a customer1 with name "Vasilis", last name "Moschou", and CPR "111aaaqpoiweu4444rqwerpiu2444233221111"
    And the customer1 is registered with the bank with an initial balance of 1000 kr
    And the customer1 is registered with Simple DTU Pay using their bank account
    And the customer1 generates 5 tokens
    Given a customer2 with name "xristina", last name "vagena", and CPR "1111111aaaqpoiweu4444rqwerpiu2444233221111"
    And the customer2 is registered with the bank with an initial balance of 1000 kr
    And the customer2 is registered with Simple DTU Pay using their bank account
    And the customer2 generates 5 tokens
    Given a merchant with name "Jan", last name "ariel", and CPR "aaaMilliodddner112222333122222www223"
    And the merchant is registered with the bank with an initial balance of 1000 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And Both customers retrieves a token
    When the merchant initiates a payment for 200 kr for both Clients at the same time
    And the balance of the customer1 at the bank is 800 kr
    And the balance of the customer2 at the bank is 800 kr
    And the balance of the merchant at the bank is 1200 kr