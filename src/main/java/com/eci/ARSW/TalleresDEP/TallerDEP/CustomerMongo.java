package com.eci.ARSW.TalleresDEP.TallerDEP;

import org.springframework.data.annotation.Id;


public class CustomerMongo {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    public CustomerMongo() {}

    public CustomerMongo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}