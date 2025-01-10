package org.acme;

import java.util.Objects;

public class Person {
    private String name;
    private String address;

    
    public Person(){}
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

        public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(address, person.address);
    }

    // Overriding hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    // Overriding toString for a readable string representation of the object
    @Override
    public String toString() {
        return "Person{name='" + name + "', address='" + address + "'}";
    }
}
