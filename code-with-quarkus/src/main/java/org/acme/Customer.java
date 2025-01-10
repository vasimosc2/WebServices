package org.acme;


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

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
}
