package com.safetyNet.alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.alert.controller.AllergyController;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.service.AllergyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@SpringBootTest
//@AutoConfigureMockMvc
public class AllergyControllerTest {
    /*@Autowired
    private MockMvc mockMvc;

    @MockBean
    AllergyService allergyService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void allergyController_createAllergy_returnCreated() throws Exception {

        Allergy allergy = new Allergy();

        allergy.setFirstName("Hamza");
        allergy.setLastName("ben");
        allergy.setNameAllergy("Pollen");

        String json = objectMapper.writeValueAsString(allergy);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/allergy")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(allergyService, Mockito.times(1)).add(any(Allergy.class));

    }

    @Test
    public void allergyController_getAllAllergiesTest_returnAllAllergies() throws Exception{

        List<Allergy> allergyList = Arrays.asList(
                new Allergy( "Hamza","ben","Pollen"),
                new Allergy("Sara", "ben","Glutin")
        );

        when(allergyService.getAll()).thenReturn(allergyList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/allergy")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Hamza"))
                .andExpect(jsonPath("$[0].lastName").value("ben" ))
                .andExpect(jsonPath("$[0].nameAllergy").value("Pollen" ))
                .andExpect(jsonPath("$[1].firstName").value("Sara"))
                .andExpect(jsonPath("$[1].lastName").value("ben"))
                .andExpect(jsonPath("$[1].nameAllergy").value("Glutin"))

                .andReturn();

        verify(allergyService, Mockito.times(1)).getAll();


    }

*/
}