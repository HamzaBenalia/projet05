package com.safetyNet.alert.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.alert.dto.fire.FireDataDto;
import com.safetyNet.alert.dto.fire.FireDto;
import com.safetyNet.alert.dto.personInfo.PersonDataDto;
import com.safetyNet.alert.dto.personInfo.PersonInfoDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDataDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDto;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.model.Person;
import com.safetyNet.alert.service.AllergyService;
import com.safetyNet.alert.service.FirestationService;
import com.safetyNet.alert.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
    @MockBean
    private FirestationService firestationService;

    @MockBean
    private AllergyService allergyService;


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void personContoller_createPerson_returnCreated() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("25 rue de toulouse");

        String json = objectMapper.writeValueAsString(person);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PersonController.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        verify(personService, times(1)).add(any(Person.class));
    }


    @Test
    public void PersonController_getAllPersonTest_returnAllPersons() throws Exception {
        List<Person> persons = Arrays.asList(
                new Person("John", "Doe", "25 Toulouse", "Toulouse", "31100", "0766764022", "John@gemail.com", "16/02/1850"),
                new Person("Jane", "ben", "30 Toulouse", "Toulouse", "31100", "0124566685", "jane@gmail.com", "10/05/1990")
        );

        when(personService.getAll()).thenReturn(persons);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/person")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].address").value("25 Toulouse"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].address").value("30 Toulouse"))
                .andReturn();

        verify(personService, times(1)).getAll();
    }

    @Test
    public void deletePersonTest() throws Exception {
        String firstName = "Hamza";
        String lastName = "Ben";

        mockMvc.perform(MockMvcRequestBuilders.delete("/person/" + firstName + "/" + lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personService, times(1)).deleteByFirstNameAndLastName(firstName, lastName);
    }

    @Test
    public void testUpdatePerson() throws Exception {
        // Créer une personne existante pour la mise à jour
        Person existingPerson = new Person("John", "Doe", "123 Main St", "Cupertino", "12345", "555-1234", "johndoe@example.com", "15/01/1997");

        // Créer une personne avec les mises à jour
        Person updatedPerson = new Person("John", "Doe", "456 New St", "Sunnyvale", "54321", "555-5678", "johndoe@gmail.com", "16/01/1995");

        // Configurer le mock pour retourner la personne existante lors de l'appel à personService.getPersonByFirstNameAndLastName()
        when(personService.getPersonByFirstNameAndLastName(existingPerson.getFirstName(), existingPerson.getLastName())).thenReturn(existingPerson);

        // Exécuter la requête PUT avec les données mises à jour
        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated successfully"));

        // Vérifier que la méthode personService.updatePerson() a été appelée avec la personne mise à jour
        verify(personService, times(1)).updatePerson(updatedPerson);
    }

    @Test
    public void testGetPersonInfos() throws Exception {
        // Create some test data
        PersonDataDto personDataDto = new PersonDataDto("John", "Doe", "Toulouse", "John@gmail.com", 30);
        Allergy allergy = new Allergy(personDataDto.getFirstName(), personDataDto.getLastName(), "Peanuts");
        Medicalrecord medicalrecord = new Medicalrecord("John", "Doe", "Doliprane : 200mg", "16/03/1993");
        PersonInfoDto personInfoDto = new PersonInfoDto(personDataDto, List.of(allergy), List.of(medicalrecord));
        List<PersonInfoDto> expectedList = List.of(personInfoDto);

        // Mock the service and set expectations
        when(personService.getPersonInfos("John", "Doe")).thenReturn(expectedList);

        // Perform the GET request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String json = result.getResponse().getContentAsString();
        List<PersonInfoDto> actualList = objectMapper.readValue(json, new TypeReference<List<PersonInfoDto>>() {
        });
        assertEquals(expectedList, actualList);
    }

    @Test //Amelioration
    public void testGetPhoneByFirestation() throws Exception {
        // Create some test data
        Person person1 = new Person("John", "Doe", "01/01/1990", "1234 Street", "City", "123456789", "john@mail.com", "16/05/1995");
        Person person2 = new Person("Jane", "Doe", "02/02/1991", "5678 Street", "City", "987654321", "jane@mail.com", "17/03/1997");
        List<Person> persons = Arrays.asList(person1, person2);
        Firestation firestation = new Firestation("1234 Street", "1");
        List<Firestation> firestations = List.of(firestation);
        PhoneAlertDataDto phoneAlertDataDto1 = new PhoneAlertDataDto("123456789");
        PhoneAlertDataDto phoneAlertDataDto2 = new PhoneAlertDataDto("987654321");

        List<PhoneAlertDataDto> phoneAlertDataDtos = new ArrayList<>();
        phoneAlertDataDtos.add(phoneAlertDataDto1);
        phoneAlertDataDtos.add(phoneAlertDataDto2);

        PhoneAlertDto phoneAlertDto = new PhoneAlertDto(phoneAlertDataDtos);

        // Mock the service and set expectations
        when(personService.getPhonesByFirestation("1")).thenReturn(phoneAlertDto);

        // Perform the GET request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("firestationNumber", "1"))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String json = result.getResponse().getContentAsString();
        PhoneAlertDto actualDto = objectMapper.readValue(json, PhoneAlertDto.class);
        assertEquals(phoneAlertDto, actualDto);
    }

    @JsonProperty("fire")
    private FireDataDto fireDataDtos;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;

    private String firstName;
    private String phone;
    private String age;
    private String stationNumber;

    @Test
    public void testGetResidentsByAddress() throws Exception {
        // Define test data
        String testAddress = "123 Main St";

        FireDataDto fireDataDto = new FireDataDto();
        fireDataDto.setFirstName("Hamza");
        fireDataDto.setPhone("0766765258");
        fireDataDto.setAge("28");
        fireDataDto.setStationNumber("1");

        Allergy allergy = new Allergy();
        allergy.setFirstName("Hamza");
        allergy.setLastName("Ben");
        allergy.setNameAllergy("Polen");

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("Hamza");
        medicalrecord.setLastName("Ben");
        medicalrecord.setNamePosology("Doliprane : 200mg");
        medicalrecord.setBirthDate("09/03/1993");


        FireDto fireDto = FireDto.builder().fireDataDto(fireDataDto).allergies(List.of(allergy)).medicalrecords(List.of(medicalrecord)).build();

        // Mock behavior of personService
        when(personService.getResidentsByAddress(anyString())).thenReturn(List.of(fireDto));

        // Call the method being tested using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", testAddress))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fire.firstName").value(fireDto.getFireDataDto().getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].allergies[0].nameAllergy").value(fireDto.getAllergies().get(0).getNameAllergy()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medicalrecords[0].namePosology").value(fireDto.getMedicalrecords().get(0).getNamePosology()));

    }


}















