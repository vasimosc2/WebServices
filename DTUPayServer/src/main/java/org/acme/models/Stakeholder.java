package org.acme.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stakeholder {
    private String stakeholderId;
    private String firstName;
    private String lastName;
    private String cprNumber;
    private String bankAccount;

    public Stakeholder() {}

    public Stakeholder(String firstName, String lastName, String cprNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cprNumber = cprNumber;
    }

    public Stakeholder(String firstName, String lastName, String cprNumber, String bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cprNumber = cprNumber;
        this.bankAccount = bankAccount;
    }
}
