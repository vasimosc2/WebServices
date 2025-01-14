package org.acme.models;

import lombok.Getter;

import java.util.UUID;


@Getter
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

    public void setId(){
        this.id = UUID.randomUUID().toString();
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }


    public void setBankAccount(String bankAccount){
        this.bankAccount = bankAccount;
    }

}