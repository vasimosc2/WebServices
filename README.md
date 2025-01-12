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
run : mvn quarkus:dev
```

**Ensure** that ```./CucumberExample/src/test/java/dtu/example/services/SimpleDtuPayService.java``` is configured to localhost:8080/ (ensure that '/' is appended).

Then, open another terminal and do:
```
cd CucumberExample
mvn test
```

### Logs:
- http://localhost:8080/payment: You can see all the Transactions
- http://localhost:8080/customer: You can see all the Customers
- http://localhost:8080/merchant: You can see all the Merchants
