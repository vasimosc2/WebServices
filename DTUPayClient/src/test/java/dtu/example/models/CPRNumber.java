package dtu.example.models;

import lombok.Getter;
import lombok.Value;

//@Value
@Getter
public class CPRNumber {
    private String cprNumber;

    public CPRNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public CPRNumber() {
    }
}
