package dtu.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String tokenId;
    private String customerId;
    private boolean used;

    public Token(){}

    public Token(String tokenId, String customerId, boolean used){
        this.tokenId = tokenId;
        this.customerId = customerId;
        this.used = used;
    }

    public boolean isUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }
}
