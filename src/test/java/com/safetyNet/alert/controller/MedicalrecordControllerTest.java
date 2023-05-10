package com.safetyNet.alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.alert.controller.MedicalrecordController;
import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.service.MedicalrecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class MedicalrecordControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    MedicalrecordService medicalrecordService;

    @Autowired
     ObjectMapper objectMapper;


    @Test
    public void medicalRecordController_createMedicalRecord_returnCreated() throws Exception{

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("hamza");
        medicalrecord.setLastName("ben");
        medicalrecord.setBirthDate("16/05/1995");
        medicalrecord.setNamePosology("Morphine : 500mg");

        String json = objectMapper.writeValueAsString(medicalrecord);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        verify(medicalrecordService, times(1)).add(any(Medicalrecord.class));

    }

    @Test
    public void medicalRecordController_getAllTest_returnedAllMedicalrecord() throws Exception{
        List<Medicalrecord> medicalrecordList = Arrays.asList(
                new Medicalrecord("Hamza", "ben", "Aspirine : 300mg", "14/01/1995"),
                new Medicalrecord("Sara", "ben", "Aznol : 200mg","18/11/1997")
        );

        when(medicalrecordService.getAll()).thenReturn(medicalrecordList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Hamza"))
                .andExpect(jsonPath("$[0].lastName").value("ben" ))
                .andExpect(jsonPath("$[0].namePosology").value("Aspirine : 300mg" ))
                .andExpect(jsonPath("$[0].birthDate").value("14/01/1995" ))
                .andExpect(jsonPath("$[1].firstName").value("Sara"))
                .andExpect(jsonPath("$[1].lastName").value("ben"))
                .andExpect(jsonPath("$[1].namePosology").value("Aznol : 200mg"))
                .andExpect(jsonPath("$[1].birthDate").value("18/11/1997"))
                .andReturn();

        verify(medicalrecordService, times(1)).getAll();

    }

    @Test
    void testDeleteMedicalrecord() throws Exception {
        // Set up test data
        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("John");
        medicalrecord.setLastName("Doe");
        medicalrecord.setNamePosology("Doliprane : 200mg");

        List<Medicalrecord> medicalrecordList = new ArrayList<>();
        medicalrecordList.add(medicalrecord);

        when(medicalrecordService.deleteMeicalrecordByFirstNameLastNameAndNamePosology("John","Doe","Doliprane : 200mg")).thenReturn(medicalrecordList);

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicalrecord)))
                .andExpect(status().isOk());

        // Verify that the delete method was called in the medicalrecordService
        verify(medicalrecordService, times(1)).deleteMeicalrecordByFirstNameLastNameAndNamePosology(medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getNamePosology());
    }

    @Test
    void testUpdateMedicalrecord() throws Exception {
        // Set up test data
        String oldNamePosology = "Doliprane : 200mg";

        Medicalrecord existingMedicalrecord = new Medicalrecord();
        existingMedicalrecord.setFirstName("John");
        existingMedicalrecord.setLastName("Doe");
        existingMedicalrecord.setNamePosology(oldNamePosology);

        List<Medicalrecord> existingMedicalrecords = new ArrayList<>();
        existingMedicalrecords.add(existingMedicalrecord);

        Medicalrecord updatedMedicalrecord = new Medicalrecord();
        updatedMedicalrecord.setFirstName("John");
        updatedMedicalrecord.setLastName("Doe");
        updatedMedicalrecord.setNamePosology("Ibuprofen : 400mg");

        List<Medicalrecord> updatedMedicalrecords = new ArrayList<>();
        updatedMedicalrecords.add(updatedMedicalrecord);

        // Mock behavior of medicalrecordService
        when(medicalrecordService.getMedicalrecorByFirstNameAndLastName(existingMedicalrecord.getFirstName(), existingMedicalrecord.getLastName()))
                .thenReturn((updatedMedicalrecords));

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord" + "/{oldNamePosology}", oldNamePosology)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMedicalrecord)))
                .andExpect(status().isOk());

        // Verify that the update method was called in the medicalrecordService
        verify(medicalrecordService, times(1)).updateMedicalrecords(updatedMedicalrecord, oldNamePosology);
    }

    @Test
    void testFindByFirstNameLastNameAndPosology() throws Exception {
        // Set up test data
        String testFirstName = "John";
        String testLastName = "Doe";
        String testNamePosology = "Doliprane : 200mg";

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName(testFirstName);
        medicalrecord.setLastName(testLastName);
        medicalrecord.setNamePosology(testNamePosology);

        // Mock behavior of medicalrecordService
        when(medicalrecordService.findByFirstNameLastNameAndPosology(testFirstName, testLastName, testNamePosology)).thenReturn(medicalrecord);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord" + "/findByFirstNameLastNameAndPosology/{firstName}/{lastName}/{namePosology}", testFirstName, testLastName, testNamePosology))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(testFirstName))
                .andExpect(jsonPath("$.lastName").value(testLastName))
                .andExpect(jsonPath("$.namePosology").value(testNamePosology));

        // Verify that the findByFirstNameLastNameAndPosology method was called in the medicalrecordService
        verify(medicalrecordService, times(1)).findByFirstNameLastNameAndPosology(testFirstName, testLastName, testNamePosology);
    }

*/

}



