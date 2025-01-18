package org.acme.resources.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTokenRequest {
    private String customerId;
    private int count;

    public GenerateTokenRequest(){}
}
