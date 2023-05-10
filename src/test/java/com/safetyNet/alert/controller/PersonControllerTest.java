package com.safetyNet.alert.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.alert.controller.PersonController;
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
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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

/*
    @Test
    public void PersonController_getAllPersonTest_returnAllPersons() throws Exception {
        List<Person> persons = Arrays.asList(
                new Person("John", "Doe" , "25 Toulouse", "Toulouse", "31100", "0766764022","John@gemail.com","16/02/1850"),
                new Person("Jane","ben", "30 Toulouse","Toulouse","31100","0124566685","jane@gmail.com","10/05/1990")
        );

        when(personService.getAll()).thenReturn(persons);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/person")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].address").value("25 Toulouse" ))
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
    public void getAllInfoTest() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        Map<Person, Medicalrecord> infoMap = new HashMap<>();
        infoMap.put(new Person(firstName, lastName, "25 Toulouse","Toulouse","31100","0766764610","John@gmail.com","12/01/1995"), new Medicalrecord(firstName, lastName,"Doliprane : 200mg","12/01/1995"));

        when(personService.getAllInfo(firstName, lastName)).thenReturn(infoMap);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/person/" + firstName + "/" + lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['" + firstName + " " + lastName + "']['medicalrecord']['namePosology']").value("Doliprane : 200mg"))
                .andReturn();

        verify(personService, times(1)).getAllInfo(firstName, lastName);
    }


    @Test
    public void testUpdatePerson() throws Exception {
        // Créer une personne existante pour la mise à jour
        Person existingPerson = new Person("John", "Doe", "123 Main St", "Cupertino", "12345", "555-1234", "johndoe@example.com","15/01/1997");

        // Créer une personne avec les mises à jour
        Person updatedPerson = new Person("John", "Doe", "456 New St", "Sunnyvale", "54321", "555-5678", "johndoe@gmail.com","16/01/1995");

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
        PersonDataDto personDataDto = new PersonDataDto("John", "Doe", "Toulouse","John@gmail.com",30);
        Allergy allergy = new Allergy("Peanuts");
        Medicalrecord medicalrecord = new Medicalrecord("John","Doe","Doliprane : 200mg","16/03/1993");
        PersonInfoDto personInfoDto = new PersonInfoDto(personDataDto, Arrays.asList(allergy), Arrays.asList(medicalrecord));
        List<PersonInfoDto> expectedList = Arrays.asList(personInfoDto);

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
        List<PersonInfoDto> actualList = objectMapper.readValue(json, new TypeReference<List<PersonInfoDto>>() {});
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetPhoneByFirestation() throws Exception {
        // Create some test data
        Person person1 = new Person("John", "Doe", "01/01/1990", "1234 Street", "City", "123456789", "john@mail.com","16/05/1995");
        Person person2 = new Person("Jane", "Doe", "02/02/1991", "5678 Street", "City", "987654321", "jane@mail.com","17/03/1997");
        List<Person> persons = Arrays.asList(person1, person2);
        Firestation firestation = new Firestation("1234 Street", "1");
        List<Firestation> firestations = Arrays.asList(firestation);
        PhoneAlertDataDto phoneAlertDataDto1 = new PhoneAlertDataDto("123456789");
        PhoneAlertDataDto phoneAlertDataDto2 = new PhoneAlertDataDto("987654321");

        List<PhoneAlertDataDto> phoneAlertDataDtos = new ArrayList<>();
        phoneAlertDataDtos.add(phoneAlertDataDto1);
        phoneAlertDataDtos.add(phoneAlertDataDto2);

        PhoneAlertDto phoneAlertDto = new PhoneAlertDto (phoneAlertDataDtos);

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

    @Test
    public void testGetResidentsByAddress() throws Exception {
        // Define test data
        String testAddress = "123 Main St";

        FireDataDto fireDataDtos = new FireDataDto();
        fireDataDtos.setFirstName("Hamza");
        fireDataDtos.setPhone("0766764612");
        fireDataDtos.setStationNumber("1");
        fireDataDtos.setAge("30");

        Allergy allergy = new Allergy();
        allergy.setFirstName("Hamza");
        allergy.setLastName("Ben");
        allergy.setNameAllergy("Polen");

        List<Allergy> allergies = new ArrayList<>();
        allergies.add(allergy);


        Medicalrecord medicalrecords = new Medicalrecord();
        medicalrecords.setFirstName("Hamza");
        medicalrecords.setLastName("Ben");
        medicalrecords.setNamePosology("Doliprane : 200mg");
        medicalrecords.setBirthDate("09/03/1993");

        List<Medicalrecord> medicalrecordList = new ArrayList<>();

        medicalrecordList.add(medicalrecords);

        FireDto expectedResidents = new FireDto (fireDataDtos, allergies, medicalrecordList);

        // Mock behavior of personService
        when(personService.getResidentsByAddress(testAddress)).thenReturn(Arrays.asList(expectedResidents));

        // Call the method being tested using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", testAddress))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fireDataDtos.firstName").value("Hamza"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].allergies[0].nameAllergy").value("Polen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medicalrecords[0].namePosology").value("Doliprane : 200mg"));

    }


*/
}















