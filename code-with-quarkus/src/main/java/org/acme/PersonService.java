package org.acme;

public class PersonService {
    Person person = new Person("Xristina", "France");
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
