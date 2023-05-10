package com.safetyNet.alert.dto.personInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.model.Medicalrecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDto {
    @JsonProperty("person")
    private PersonDataDto personDataDto;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;
}
