package com.safetyNet.alert.dto.childAlert;

import com.safetyNet.alert.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildDto {
    private List<Person> adults;
    private List<ChildDataDto> children;
}
