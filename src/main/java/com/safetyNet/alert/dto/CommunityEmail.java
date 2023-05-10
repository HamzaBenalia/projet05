package com.safetyNet.alert.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommunityEmail {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
}
