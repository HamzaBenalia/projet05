package com.safetyNet.alert.dto.phoneAlert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAlertDto {
    @JsonProperty("PhoneAlert")
    private List<PhoneAlertDataDto> phoneAlertDataDtos;
}
