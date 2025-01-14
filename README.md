# DTU Pay - Jenkins Repository
---

Current version of server is implemented onto Jenkins **NOTE** (Not this repo currently - due to webhook).

## Instructions

### Remote VM server testing

For manual testing clone the repository

```
git clone https://github.com/vasimosc2/WebServices/
```

For testing our remote VM server on dtu compute, run the following to start Junit testing with client:

```
cd CucumberExample
mvn test
```

### Local server testing

To start the server do:
```
cd code-with-quarkus
mvn quarkus:dev
```

**Ensure** that ```./CucumberExample/src/test/java/dtu/example/services/SimpleDtuPayService.java``` is configured to localhost:8081/ (ensure that '/' is appended).

Then, open another terminal and do:
```
cd CucumberExample
mvn test
```

### Logs:
- http://localhost:8081/payment: You can see all the Transactions
- http://localhost:8081/customer: You can see all the Customers
- http://localhost:8081/merchant: You can see all the Merchants




# Code Explantation
The localhost or the Jenkins Docker is the <SimpleDTUPay>

The first file you must look at is the CucumberExample/feratures/payment.feature
There we identify the Test (or better the steps of the Test) that we are going to run
Whenever we face a Line ,for example:  

 Given a customer with name "Vasilis", last name "Moschou", and CPR "56re!e!!ff"

There you be an execution in the CucumberExample/src/test/java/dtu/example/SimpleDtuPayTest.java

The above example will execute the function:  public void aCustomerWithNameLastNameAndCPR(String string, String string2, String string3)
Because it the one below the notation: @Given("a customer with name {string}, last name {string}, and CPR {string}")


Let's explain what every function is doing:
1)  public void aCustomerWithNameLastNameAndCPR(String string, String string2, String string3):
    This function just creates a Customer and ensures that is created correctly (Using the assert)
    Note: The customer is private variable because we will need it later (otherwise it would be only execible in the function)

2)  public void theCustomerIsRegisteredWithTheBankWithAnInitialBalanceOfKr(int money):
    This creates a bank account in the bank. It is important to initialaize a user like this because the functions requieres a user and not a customer/merhcant
    You can see all the Bank objects at CucumberExample/src/main/java/dtu/we/fastmoney

    customerAccountId = bankService.createAccountWithBalance(user,BigDecimal.valueOf(money));// This is the accountcreating and it returns the customerAccountId
    it is important to use a private variable for this as this will be used a lot later

3) @Given("the customer is registered with Simple DTU Pay using their bank account"):
    As stated we should not use the customer directly but we should use the same accountId we store from before
    to fetch information for the Customer.
    So we create an Account: Account account = bankService.getAccount(customer.getBankAccount());
    And we send the information to SimpleDtuPay, this is done at CucumberExample/src/test/java/dtu/example/service/SimpleDTUPayService.java
    we send the User and the BankAccount, in the server side it justs create a new Customer using those information and then it appends it in a list
    which is visible at : http://localhost:8081/customer

The same exact procedure is happening with the merchant as well