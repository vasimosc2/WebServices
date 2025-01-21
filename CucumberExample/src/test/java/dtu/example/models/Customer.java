package dtu.example.models;

public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String cprNumber;
    private String bankAccount;

   
    public Customer() {}

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getCprNumber() {
        return cprNumber;
    }
    
    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }


    public String getBankAccount(){
        return bankAccount;
    }

    public void setBankAccount(String bankAccount){
        this.bankAccount = bankAccount;
    }

}