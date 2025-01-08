To start the application please do:

cd <ClonedArea>
java -jar target/quarkus-app/quarkus-run.jar

The application will run at :
http://localhost:8081/person

Testing: (This is already done, but if you want to test it yourself)
cd <ClonedArea>
mvn test


# Ensure that nothing else is running at localhost 8081

To do so :
sudo lsof -i :8081 // To Find the PID that runs inside the port 8081
sudo kill -9 <PID> // To kill this process
