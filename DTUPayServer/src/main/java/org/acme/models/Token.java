package org.acme.models;

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
//        this.tokenId = tokenId;
        this.used = used;
    }

    public boolean isUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }
}
