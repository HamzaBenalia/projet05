package com.safetyNet.alert.dto;

import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class FirestationAlert {


    private Person persons;
    private FirestationAlert firestationAlert;

    public FirestationAlert(Person person, List<Firestation> firestationList) {
    }
}
