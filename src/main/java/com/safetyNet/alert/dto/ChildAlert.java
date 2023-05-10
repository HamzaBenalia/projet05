package com.safetyNet.alert.dto;

import com.safetyNet.alert.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlert {

    private Person persons;
}
