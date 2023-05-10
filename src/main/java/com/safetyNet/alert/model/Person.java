package com.safetyNet.alert.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private String birthdate;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
