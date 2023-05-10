package com.safetyNet.alert.dto.personInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDataDto {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int age;

}
