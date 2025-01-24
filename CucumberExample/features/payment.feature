Feature: Payment
  Scenario: Double Payment
    Given a customer1 with name "Vasilis", last name "Moschou", and CPR "gfdgdfsewuyeytsfgdwtrfafdgfdadfadfgs332df"
    And the customer1 is registered with the bank with an initial balance of 1000 kr
    And the customer1 is registered with Simple DTU Pay using their bank account
    And the customer1 generates 5 tokens
    Given a customer2 with name "xristina", last name "vagena", and CPR "gfdgfdgdshgy"
    And the customer2 is registered with the bank with an initial balance of 900 kr
    And the customer2 is registered with Simple DTU Pay using their bank account
    And the customer2 generates 5 tokens
    Given a merchant with name "Jan", last name "ariel", and CPR "ghksgffsdgsff4hfhgfhfdadafafd3ho21gfokhf"
    And the merchant is registered with the bank with an initial balance of 1000 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And Both customers retrieves a token
    When the merchant initiates a payment for 200 kr 300 for both Clients at the same time
    And the balance of the customer1 at the bank is 800 kr
    And the balance of the customer2 at the bank is 600 kr
    And the balance of the merchant at the bank is 1500 kr
  
  Scenario: Simple Paymnet
    Given a customer with name "John", last name "Doe", and CPR "1sgsfdg234sghrtfg532632afdasdfadfssa7890reas"
    And the customer is registered with the bank with an initial balance of 500 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 3 tokens
    Given a merchant with name "Jane", last name "Smith", and CPR "098sgdfsdghthr76543r2r34321afd2bgdshjreadasd"
    And the merchant is registered with the bank with an initial balance of 200 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant initiates a payment for 100 kr
    Then the payment is successful
    And the balance of the customer at the bank is 400 kr
    And the balance of the merchant at the bank is 300 kr

  Scenario: Request Too Many Tokens
    Given a customer with name "John", last name "Doe", and CPR "1sf23sf4sghs5aghtrhrfehgnads32adad67890reas"
    And the customer is registered with the bank with an initial balance of 500 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 7 tokens
  
  Scenario: Invalid Token
    Given a customer with name "John", last name "Doe", and CPR "12hsfggfh3421adfhrthra4grehtwr53267890reas"
    And the customer is registered with the bank with an initial balance of 500 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 3 tokens
    Given a merchant with name "Jane", last name "Smith", and CPR "09jdsfbsd876asfadhrthrhfhgEFDV23dv5434321readasd"
    And the merchant is registered with the bank with an initial balance of 200 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant initiates two payments for 100 kr
    Then the payment is fault
  
  Scenario: Request Exceeds Maximum Tokens
    Given a customer with name "John", last name "Doe", and CPR "1nsgnfssb234214grehtahrthrdfadfbwr53267890reas"
    And the customer is registered with the bank with an initial balance of 500 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 3 tokens
    And the customer generates 5 tokens

  Scenario: Customer Not Enough Funds
    Given a customer with name "John", last name "Dofe", and CPR "1afsdafdadf23453263gdfgsdfgsdfhrhr2afdasdfadfssa7890reas"
    And the customer is registered with the bank with an initial balance of 200 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 3 tokens
    Given a merchant with name "Jane", last name "Smith", and CPR "0sfgsgfsdfgs98gfdgfdgdf765htrhhrthr43r2r34321afd2bgdshjreadasd"
    And the merchant is registered with the bank with an initial balance of 300 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant initiates a payment for 300 kr
    Then the payment is fault

  Scenario: Try to add another Customer with the Same CPR number
    Given a customer with name "John", last name "Dofe", and CPR "1afsdafdadf23453263gdfgsdfgsdfhrhr2afdasdfadfssa7890reas"
    And the customer is registered with the bank with an initial balance of 200 kr
    And the customer is registered with Simple DTU Pay using their bank account error
  
  Scenario: Invalid MerchantId
    Given a customer with name "John", last name "Doe", and CPR "1afsdafdadf2345kjhkjghk32632afhtrhrthrtdasdfadfssa7890reas"
    And the customer is registered with the bank with an initial balance of 500 kr
    And the customer is registered with Simple DTU Pay using their bank account
    And the customer generates 3 tokens
    Given a merchant with name "Jane", last name "Smith", and CPR "098sgdfshghfghfddg76543r2r34321afd2bgdshjreadaadsfafdsd"
    And the merchant is registered with the bank with an initial balance of 200 kr
    And the merchant is registered with Simple DTU Pay using their bank account
    And the customer retrieves a token
    When the merchant with id "asadsa" initiates a payment for 100 kr
    Then the payment is fault

#  Scenario: Delete Customer Then Token becomes invalid
#    Given a customer with name "John", last name "Doe", and CPR "1sgsfasfafddg234sfg532632afd3wqewasdfadfssa7890reas"
#    And the customer is registered with the bank with an initial balance of 500 kr
#    And the customer is registered with Simple DTU Pay using their bank account
#    And the customer generates 3 tokens
#    Given a merchant with name "Jane", last name "Smith", and CPR "098afdadfadfsgdfsdg76543r2r34321afd2bgdshjreadaadsfafdsd"
#    And the merchant is registered with the bank with an initial balance of 200 kr
#    And the merchant is registered with Simple DTU Pay using their bank account
#    And the customer retrieves a token
#    When the customer unregister from Simple DTU Pay
#    When the merchant initiates a payment for 300 kr
#    Then the payment is fault