package org.acme.models;

public class Customer extends Stakeholder {


    public Customer() {}

    public Customer(String firstName, String lastName, String cprNumber, String bankAccount) {
        super(firstName, lastName, cprNumber, bankAccount);
    }

}