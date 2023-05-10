package com.safetyNet.alert.dto.stationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StationInfoDto {
    @JsonProperty("PersonByStation")
    private List<PersonStationDto> personStationDtos;
    private int adult;
    private int minor;
}
