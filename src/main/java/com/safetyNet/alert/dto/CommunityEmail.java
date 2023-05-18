package com.safetyNet.alert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityEmail {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
}
