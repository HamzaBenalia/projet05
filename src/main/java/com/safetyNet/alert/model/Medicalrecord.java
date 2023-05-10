package com.safetyNet.alert.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicalrecord {
    private String firstName;
    private String lastName;
    private String namePosology;
    private String birthDate;
}
