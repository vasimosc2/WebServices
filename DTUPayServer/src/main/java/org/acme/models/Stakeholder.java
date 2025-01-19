package org.acme.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stakeholder {
    private StakeholderId id;
    private String firstName;
    private String lastName;
    private CPRNumber cprNumber;
    private BankAccount bankAccount;

    public Stakeholder() {}

    public Stakeholder(String firstName, String lastName, String cprNumber, String bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cprNumber = new CPRNumber(cprNumber);
        this.bankAccount = new BankAccount(bankAccount);
    }
}
