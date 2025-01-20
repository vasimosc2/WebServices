package dtu.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String tokenId;
    private boolean used;

    public Token(){}

    public Token(String tokenId, boolean used){
        this.tokenId = tokenId;
//        this.customerId = customerId;
        this.used = used;
    }
}
