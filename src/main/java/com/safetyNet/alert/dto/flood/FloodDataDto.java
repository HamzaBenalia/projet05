package com.safetyNet.alert.dto.flood;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloodDataDto {
    @JsonIgnore
    private String lastName;
    private String firstName;
    private String phone;
    private String age;
}
