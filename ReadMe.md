Clone the repository

cd code-with-quarkus
run : mvn quarkus:dev

In another Terminal

cd CucumberExample
run: mvn test

http://localhost:8081/payment: It will display all the payments done it your feature (If you run test many items it will add more in the array)
http://localhost:8081/customer: It will display the Final Customer in your features
http://localhost:8081/merchant: It will display the Final Merchant in your features

http://localhost:8081/person: It will display the Final Person (The tests here check if the updates are done correctly)
http://localhost:8081/hello: Hello RestApi