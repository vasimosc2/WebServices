package org.acme.models;

import lombok.Getter;
import lombok.Value;

@Getter
//@Value
public class CPRNumber {
    private String cprNumber;

    public CPRNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public CPRNumber() {
    }
}
