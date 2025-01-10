Feature: Payment
    Scenario:Successful Payment
    Given a customer with name "Susan"
    Given the customer is registered with Simple DTU Pay
    Given a merchant with name "Daniel"
    Given the merchant is registered with Simple DTU Pay
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is successful