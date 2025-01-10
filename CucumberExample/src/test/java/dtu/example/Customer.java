package dtu.example;

import java.util.UUID;

public class Customer {
    private String id;
    private String name;

    public Customer() {
        
    }

    public Customer(String name) {
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
