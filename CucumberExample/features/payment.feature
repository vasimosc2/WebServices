Feature: Payment
  Scenario: Double Payment
    Given a customer1 with name "Vasilis", last name "Moschou", and CPR "gfdgdfsgfdgs332df"
    And the customer1 is registered with the bank with an initial balance of 1000 kr
    And the customer1 is registered with Simple DTU Pay using their bank account
    And the customer1 generates 5 tokens
    Given a customer2 with name "xristina", last name "vagena", and CPR "khl12klhklhfg"
    And the customer2 is registered with the bank with an initial balance of 900 kr
    And the customer2 is registered with Simple DTU Pay using their bank account
    And the customer2 generates 5 tokens
    Given a merchant with name "Jan", last name "ariel", and CPR "ghkf43hogfokhf"
    And the merchant is registered with the bank with an initial balance of 1000 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And Both customers retrieves a token
    When the merchant initiates a payment for 200 kr 300 for both Clients at the same time
    And the balance of the customer1 at the bank is 800 kr
    And the balance of the customer2 at the bank is 600 kr
    And the balance of the merchant at the bank is 1500 kr
  
  Scenario: Simple Paymnet
    Given a customer with name "John", last name "Doe", and CPR "123453267890reas"
    And the customer is registered with the bank with an initial balance of 500 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 3 tokens
    Given a merchant with name "Jane", last name "Smith", and CPR "098765434321readasd"
    And the merchant is registered with the bank with an initial balance of 200 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant initiates a payment for 100 kr
    Then the payment is successful
    And the balance of the customer at the bank is 400 kr
    And the balance of the merchant at the bank is 300 kr