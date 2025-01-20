package dtu.example.models;

import lombok.Getter;
import lombok.Value;

//@Value
@Getter
public class BankAccount {
    private String bankAccountId;

    public BankAccount(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public BankAccount() {
    }
}
