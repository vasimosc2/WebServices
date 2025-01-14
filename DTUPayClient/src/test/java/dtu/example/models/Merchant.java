package dtu.example.models;

import lombok.Getter;


public class Merchant extends Stakeholder {

    public Merchant() {}

    public Merchant(String firstName, String lastName, String cprNumber, String bankAccount) {
        super(firstName, lastName, cprNumber, bankAccount);
    }

}