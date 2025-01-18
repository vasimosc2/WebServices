package models;


public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String cprNumber;
    private String bankAccount;

   
    public Customer() {}

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

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
