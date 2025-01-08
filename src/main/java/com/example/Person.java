package com.example;

import javax.json.bind.annotation.JsonbProperty;

public class Person {
    @JsonbProperty("name")
    private String name;
    
    @JsonbProperty("address")
    private String address;

    public Person() {}

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
