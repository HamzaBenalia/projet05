package com.safetyNet.alert.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allergy {
    private String firstName;
    private String lastName;
    private String nameAllergy;

    public Allergy(String nameAllergy) {
    }
}
