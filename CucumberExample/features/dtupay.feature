Feature: Payment
    Scenario:Successful Payment
    Given a customer with name "Susan"
    Given the customer is registered with Simple DTU Pay
    Given a merchant with name "Daniel"
    Given the merchant is registered with Simple DTU Pay
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is successful

    Scenario: List of payments
    Given a customer with name "Vasilis", who is registered with Simple DTU Pay
    And a merchant with name "Xristina", who is registered with Simple DTU Pay
    Given a successful payment of "10" kr from the customer to the merchant
    When the manager asks for a list of paymentsThen the list contains a payments where customer "Vasilis" paid "10" kr to merchant "Xristina"