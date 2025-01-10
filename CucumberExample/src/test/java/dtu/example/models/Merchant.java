package dtu.example.models;

import java.util.UUID;

public class Merchant {
    private String id;
    private String name;

    public Merchant(){}
    public Merchant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(){
        this.id = UUID.randomUUID().toString();
    }
    public String getId(){
        return this.id;
    }
}
