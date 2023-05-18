package com.safetyNet.alert.dto.fire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.model.Medicalrecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireDto {
    @JsonProperty("fire")
    private FireDataDto fireDataDto;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;

}
