package org.acme.resources.dto;

import lombok.Getter;
import lombok.Setter;
import org.acme.models.StakeholderId;

@Getter
@Setter
public class GenerateTokenRequest {
    private String customerId;
    private int count;

    public GenerateTokenRequest(){}
}
