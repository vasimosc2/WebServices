package models;

public class Token {
    
    private String id;
    private boolean used;
    public Token() {
    }

    public Token(String id, boolean used) {
        this.id = id;
        this.used = used;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }
}
