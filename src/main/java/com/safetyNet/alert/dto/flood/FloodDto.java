package com.safetyNet.alert.dto.flood;

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
public class FloodDto {
    @JsonProperty("flood")
    private FloodDataDto floodDataDto;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;
}
