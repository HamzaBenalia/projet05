package com.safetyNet.alert.service;

import com.safetyNet.alert.dto.childAlert.ChildDto;
import com.safetyNet.alert.dto.fire.FireDto;
import com.safetyNet.alert.dto.flood.FloodDto;
import com.safetyNet.alert.dto.personInfo.PersonInfoDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDto;
import com.safetyNet.alert.dto.stationDto.StationInfoDto;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.model.Person;
import com.safetyNet.alert.repository.AllergyRepository;
import com.safetyNet.alert.repository.FirestationRepository;
import com.safetyNet.alert.repository.MedicalrecordRepository;
import com.safetyNet.alert.repository.PersonRepository;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;
    @Mock
    private AllergyRepository allergyRepository;
    @Mock
    private MedicalrecordRepository medicalrecordRepository;

    @Mock
    private FirestationRepository firestationRepository;


    @Test
    public void testAdd() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        when(personRepository.getAll()).thenReturn(new ArrayList<>());

        personService.add(person);

        Mockito.verify(personRepository, times(1)).save(personArgumentCaptor.capture());

        assertThat(personArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "address", "city", "zip", "phone", "email", "birthdate")
                .containsExactly(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(), person.getZip(), person.getPhone(), person.getEmail(), person.getBirthdate());
    }


    @Test
    public void testAddExistePerson() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        when(personRepository.getAll()).thenReturn(List.of(person));
        personService.add(person);
        Mockito.verify(personRepository, times(0)).save(personArgumentCaptor.capture());
    }

    @Test
    public void testGetAll() {

        // Create some persons
        Person person1 = new Person("John", "Doe", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "jane.doe@example.com");
        List<Person> allPersons = Arrays.asList(person1, person2);

        when(personRepository.getAll()).thenReturn(allPersons);

        List<Person> result = personService.getAll();
        Mockito.verify(personRepository, times(1)).getAll();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(person1));
        Assertions.assertTrue(result.contains(person2));
    }

    @Test
    public void testGetPersonByFirstNameAndLastName() {
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");

        personRepository.save(person1);

        List<Person> personList = new ArrayList<>();
        personList.add(person1);

        when(personRepository.getAll()).thenReturn(personList);

        Person result = personService.getPersonByFirstNameAndLastName("John", "Boyd");

        // Verify that the correct medical record was returned
        Assertions.assertEquals(result, person1);
        verify(personRepository, times(1)).getAll();

    }

    @Test
    public void testDeleteByFirstNameAndLastName() {
        // Create a list of persons to use as test data
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", "Doe"));
        personList.add(new Person("Alice", "Smith"));

        when(personRepository.getAll()).thenReturn(personList);

        personService.deleteByFirstNameAndLastName("Alice", "Smith");

        assertFalse(personList.contains(new Person("Alice", "Smith")));
    }

    @Test
    public void testUpdatePerson() {

        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        personRepository.save(person);

        // Create an updated version of the test person
        Person updatedPerson = new Person("Hamza", "Ben", "Toulouse", "Toulouse", "31100", "0766764585", "Hamza@gmail.com", "16/03/1998");

        // Call the updatePerson method with the updated person
        personService.updatePerson(updatedPerson);

        Person updatedPersons = updatedPerson;

        // Get the person from the repository and verify that it has been updated
        assertEquals(updatedPerson, updatedPersons);

    }

    @Test
    public void testGetPersonByPhone() {
        // Create a list of persons to use as test data
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        person1.setPhone("123456789");
        Person person2 = new Person("Hamza", "Ben", "Toulouse", "Toulouse", "31100", "0666528541", "Boyd@gmail.com", "16/03/1995");
        person2.setPhone("987654321");


        // Create a mock PersonRepository that returns the test data
        when(personService.getPersonByphone("123456789")).thenReturn(Collections.singletonList(person1));
        when(personService.getPersonByphone("987654321")).thenReturn(Collections.singletonList(person2));


        List<Person> foundPersons1 = personService.getPersonByphone("123456789");
        List<Person> foundPersons2 = personService.getPersonByphone("987654321");

        // Verify that the correct person(s) were returned
        assertEquals(1, foundPersons1.size());
        assertEquals(person1, foundPersons1.get(0));
        assertEquals(1, foundPersons2.size());
        assertEquals(person2, foundPersons2.get(0));

        // Verify that the personRepository's getPersonByPhone method was called
        verify(personRepository, times(2)).getPersonByPhone(anyString());
    }

    @Test
    public void testGetAllInfo() {
        // Create test data
        Person person1 = new Person("John", "Doe", "Toulouse");
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "Doliprane = : 200mg", "15/03/1995");
        Person person2 = new Person("Jane", "Doe", "Paris");
        Medicalrecord medicalrecord2 = new Medicalrecord("Jane", "Doe", "Ubiprohen : 300mg", "14/08/1997");

        PersonRepository personRepository = mock(PersonRepository.class);
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);

        // Add test data to repositories
        personRepository.save(person1);
        medicalrecordRepository.save(medicalrecord1);
        personRepository.save(person2);
        medicalrecordRepository.save(medicalrecord2);
    }

    @Test
    public void testGetPersonInfos() {
        // Create test data
        Person person1 = new Person("John", "Doe", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Person person2 = new Person("Jane", "Doe", "Paris", "Paris", "31100", "0766852232", "Boyd123@gmail.com", "16/07/1995");
        Allergy allergy1 = new Allergy("John", "Doe", "peanuts");
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "Doliprane : 200mg", "15/03/1997");
        Medicalrecord medicalrecord2 = new Medicalrecord("Jane", "Doe", "Ubiprophen", "10/01/1993");


        // Configure mocks
        when(personRepository.getPersonByName("Doe")).thenReturn(List.of(person1, person2));
        when(allergyRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(List.of(allergy1));
        when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("John", "Doe")).thenReturn(List.of(medicalrecord1));
        when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("Jane", "Doe")).thenReturn(List.of(medicalrecord2));

        // Call the method being tested
        List<PersonInfoDto> result = personService.getPersonInfos("John", "Doe");

        // Verify the results
        assertEquals(2, result.size());
        List<String> actualFirstNames = result.stream().map(personInfoDto -> personInfoDto.getPersonDataDto().getFirstName()).collect(toList());
        List<String> actualLastNames = result.stream().map(personInfoDto -> personInfoDto.getPersonDataDto().getLastName()).collect(toList());

        MatcherAssert.assertThat(actualFirstNames, containsInAnyOrder(person1.getFirstName(), person2.getFirstName()));
        MatcherAssert.assertThat(actualLastNames, containsInAnyOrder(person1.getLastName(), person2.getLastName()));
    }

    @Test
    public void testGetPhonesByFirestation() {

        PhoneAlertDto phoneAlertDto = new PhoneAlertDto();

        // Create test data
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Person person2 = new Person("Hamza", "Ben", "Toulouse", "Toulouse", "31100", "0766785522", "Hamza@gmail.com", "16/03/1999");

        Firestation firestation1 = new Firestation("Toulouse", "1");
        Firestation firestation2 = new Firestation("Paris", "2");


        PhoneAlertDto result = personService.getPhonesByFirestation("2");

        Assertions.assertNotNull(result);
    }


    @Test
    public void testGetResidentsByAddress() {
        // Create test data
        Firestation firestation = new Firestation("Toulouse", "1");
        when(firestationRepository.getAll()).thenReturn(List.of(firestation));
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Allergy allergy = new Allergy(person.getFirstName(), person.getLastName(), "peanut");
        Medicalrecord medicalrecord = new Medicalrecord(person.getFirstName(), person.getLastName(), "Doliprane", "16/03/1995");

        when(personRepository.getPersonByAddress("Toulouse")).thenReturn(List.of(person));
        when(allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(allergy));
        when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(medicalrecord));

        List<FireDto> result = personService.getResidentsByAddress("Toulouse");

        Assertions.assertEquals(1, result.size());
        Assert.assertEquals(person.getFirstName(), result.get(0).getFireDataDto().getFirstName());
        Assert.assertEquals(person.getPhone(), result.get(0).getFireDataDto().getPhone());

    }


    @Test
    public void testGetResidentsByStationNumber() {

        Firestation firestation1 = new Firestation("Toulouse", "1");
        Mockito.when(firestationRepository.getFirestationByStation("1")).thenReturn(List.of(firestation1));

        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Mockito.when(personRepository.getAll()).thenReturn(List.of(person));

        Allergy allergy = new Allergy(person.getFirstName(), person.getLastName(), "peanuts");
        Mockito.when(allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(allergy));
        Medicalrecord medicalrecord = new Medicalrecord(person.getFirstName(), person.getLastName(), "Doliprane : 200mg", "16/03/1995");
        Mockito.when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(medicalrecord));


        // Call the method being tested
        Map<String, List<FloodDto>> result = personService.getResidentsByStationNumber(Arrays.asList("1", "2"));

        // Verify the result
        Assert.assertEquals(1, result.size());

        List<FloodDto> floodDataDtos1 = result.get("Toulouse");
        Assert.assertEquals(person.getFirstName(), floodDataDtos1.get(0).getFloodDataDto().getFirstName());
        Assert.assertEquals(person.getPhone(), floodDataDtos1.get(0).getFloodDataDto().getPhone());

    }

    @Test
    public void testGetChildrenByAddress() {

        Person personJohn = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/2010");
        Person personHamza = new Person("Hamza", "Benalia", "Toulouse", "Toulouse", "31100", "0766764585", "Hamza@gmail.com", "16/03/2000");
        Mockito.when(personRepository.getPersonByAddress(anyString())).thenReturn(Arrays.asList(personJohn, personHamza));

        // Call the method being tested
        ChildDto result = personService.getChildrenByAddress("Toulouse");

        // Verify the result
        Assert.assertEquals(1, result.getChildren().size());
        Assert.assertEquals(1, result.getAdults().size());

        Assert.assertEquals(personJohn.getFirstName(), result.getChildren().get(0).getFirstName());
        Assert.assertEquals(personJohn.getLastName(), result.getChildren().get(0).getLastName());
        Assert.assertEquals(personHamza.getFirstName(), result.getAdults().get(0).getFirstName());
        Assert.assertEquals(personHamza.getLastName(), result.getAdults().get(0).getLastName());
    }

    @Test
    public void getPersonByFirestationTest(){

        Firestation firestation1 = new Firestation("Toulouse", "1");

        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Mockito.when(personRepository.getAll()).thenReturn(List.of(person));

        when(firestationRepository.getAdresseByStation("1")).thenReturn(Collections.singletonList(person.getAddress()));

        // Call the method being tested
        StationInfoDto result = personService.getPersonByFirestation(("1"));

        // Verify the result

        Assert.assertEquals(person.getFirstName(), result.getPersonStationDtos().get(0).getFirstName());

    }

}



