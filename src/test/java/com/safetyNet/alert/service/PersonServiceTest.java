package com.safetyNet.alert.service;

import com.safetyNet.alert.dto.fire.FireDataDto;
import com.safetyNet.alert.dto.fire.FireDto;
import com.safetyNet.alert.dto.flood.FloodDto;
import com.safetyNet.alert.dto.personInfo.PersonInfoDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDataDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDto;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.model.Person;
import com.safetyNet.alert.repository.AllergyRepository;
import com.safetyNet.alert.repository.FirestationRepository;
import com.safetyNet.alert.repository.MedicalrecordRepository;
import com.safetyNet.alert.repository.PersonRepository;
import com.safetyNet.alert.service.AllergyService;
import com.safetyNet.alert.service.FirestationService;
import com.safetyNet.alert.service.MedicalrecordService;
import com.safetyNet.alert.service.PersonService;
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
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
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




    @Test
    public void testAdd() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        when(personRepository.getAll()).thenReturn(new ArrayList<>());

        personService.add(person);

        Mockito.verify(personRepository, times(1)).save(personArgumentCaptor.capture());

        assertThat(personArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "address", "city", "zip","phone","email","birthdate")
                .containsExactly(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(), person.getZip(), person.getPhone(), person.getEmail(), person.getBirthdate());
    }


    @Test
    public void testAddExistePerson() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        when(personRepository.getAll()).thenReturn(Arrays.asList(person));
        personService.add(person);
        Mockito.verify(personRepository, times(0)).save(personArgumentCaptor.capture());
    }
    @Test
    public void testGetAll() {

        // Create some persons
        Person person1 = new Person("John", "Doe", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "jane.doe@example.com");
        List<Person> allPersons = Arrays.asList(person1,person2);

        when(personRepository.getAll()).thenReturn(allPersons);

        List<Person> result = personService.getAll();
        Mockito.verify(personRepository, times(1)).getAll();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(person1));
        Assertions.assertTrue(result.contains(person2));
    }

    //@Test
    public void testGetPersonByFirstNameAndLastName() {
        // Create a mock repository with sample data
        List<Person> persons = Arrays.asList(
                new Person("John", "Doe", "john.doe@example.com"),
                new Person("Jane", "Doe", "jane.doe@example.com"),
                new Person("Bob", "Smith", "bob.smith@example.com")
        );
        PersonRepository personRepository = mock(PersonRepository.class);
        when(personRepository.getAll()).thenReturn(persons);

        // Create a new instance of PersonService and set the mocked repository
        PersonService personService = mock(PersonService.class);

        // Test getting a person that exists
        Person person = new Person("John", "Doe", "john.doe@example.com");
        personService.add(person);
        personService.getPersonByFirstNameAndLastName("John", "Doe");
        assertNotNull(person);

        // Test getting a person that doesn't exist
        Person janeSmith = personService.getPersonByFirstNameAndLastName("Jane", "Smith");
        assertNull(janeSmith);
    }

    //@Test
    public void testDeleteByFirstNameAndLastName() {
        // Create a list of persons to use as test data
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", "Doe"));
        personList.add(new Person("Alice", "Smith"));


        // Create a PersonService with the mock repository
        PersonService personService = mock(PersonService.class);

        Mockito.when(personService.getAll()).thenReturn(personList);


        // Call the deleteByFirstNameAndLastName method
        personList = personService.deleteByFirstNameAndLastName("Alice", "Smith");

        // Verify that the correct person was removed from the list

        assertFalse(personList.contains(new Person("Alice", "Smith")));
    }

    //@Test
    public void testUpdatePerson() {
        // Create a test person
        PersonRepository personRepository = mock(PersonRepository.class);
        PersonService personService = mock(PersonService.class);
        Person person = new Person("John", "Doe");
        personRepository.save(person);

        // Create an updated version of the test person
        Person updatedPerson = new Person("Hamza", "Ben");

        // Call the updatePerson method with the updated person
        personService.updatePerson(updatedPerson);

        Person updatedPersons = updatedPerson;


        // Get the person from the repository and verify that it has been updated
        assertEquals(updatedPerson, updatedPersons);

    }

    //@Test
    public void testGetPersonByPhone() {
        // Create a list of persons to use as test data
        Person person1 = new Person("John", "Doe");
        person1.setPhone("123456789");
        Person person2 = new Person("Alice", "Smith");
        person2.setPhone("987654321");
        PersonService personService = mock(PersonService.class);

        // Create a mock PersonRepository that returns the test data
        when(personService.getPersonByphone("123456789")).thenReturn(Collections.singletonList(person1));
        when(personService.getPersonByphone("987654321")).thenReturn(Collections.singletonList(person2));

        // Create a PersonService instance and call the getPersonByphone method

        List<Person> foundPersons1 = personService.getPersonByphone("123456789");
        List<Person> foundPersons2 = personService.getPersonByphone("987654321");

        // Verify that the correct person(s) were returned
        assertEquals(1, foundPersons1.size());
        assertEquals(person1, foundPersons1.get(0));
        assertEquals(1, foundPersons2.size());
        assertEquals(person2, foundPersons2.get(0));

        // Verify that the personRepository's getPersonByPhone method was called
        verify(personService, times(2)).getPersonByphone(anyString());
    }

    //@Test
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


        // Call the method being tested
       /* Map<Person, Medicalrecord> result = ("John", "Doe");

        // Verify the results
        assertEquals(1, result.size());
        assertTrue(result.containsKey(person1));
        assertTrue(result.containsValue(medicalrecord1));*/
    }

    /*List<Person> personList = personRepository.getPersonByName(lastName);
        for (Person person : personList) {
        List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());*/
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

        MatcherAssert.assertThat(actualFirstNames , containsInAnyOrder(person1.getFirstName(), person2.getFirstName()));
        MatcherAssert.assertThat(actualLastNames , containsInAnyOrder(person1.getLastName(), person2.getLastName()));
    }

    //@Test
    public void testGetPhonesByFirestation() {

        PhoneAlertDto phoneAlertDto = new PhoneAlertDto();

        // Create test data
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Person person2 = new Person("Hamza", "Ben", "Toulouse", "Toulouse", "31100", "0766785522", "Hamza@gmail.com", "16/03/1999");


        Firestation firestation1 = new Firestation("Toulouse", "1");
        Firestation firestation2 = new Firestation("Paris", "1");
        Firestation firestation3 = new Firestation("789 Maple St", "2");

        PersonRepository personRepository = mock(PersonRepository.class);
        FirestationRepository firestationRepository = mock(FirestationRepository.class);
        PersonService personService = mock(PersonService.class);

        // Add test data to repositories
        personRepository.save(person1);
        personRepository.save(person2);

        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        PhoneAlertDataDto phoneAlertDataDto = new PhoneAlertDataDto();
        phoneAlertDataDto.setPhone(person1.getPhone());

        List<PhoneAlertDataDto> phoneAlertDataDtos = new ArrayList<>();
        phoneAlertDataDtos.add(phoneAlertDataDto);

        phoneAlertDto.setPhoneAlertDataDtos(phoneAlertDataDtos);

        Assertions.assertNotNull(phoneAlertDto);

    }

    //@Test
    public void testGetResidentsByAddress() {
        // Create test data
        Firestation firestation1 = new Firestation("Toulouse", "1");
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Allergy allergy1 = new Allergy("John", "Boyd", "peanut");
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "Doliprane","16/03/1995");

        FireDataDto fireDataDto = mock(FireDataDto.class);
        FireDto fireDto = mock(FireDto.class);
        FirestationService firestationService = mock(FirestationService.class);
        PersonService personService = mock(PersonService.class);
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);
        AllergyService allergyService = mock(AllergyService.class);

        // Add test data to repositories
        firestationService.add(firestation1);
        personService.add(person1);
        allergyService.add(allergy1);
        medicalrecordService.add(medicalrecord1);

        // Call the method being tested
        List<FireDto> result = personService.getResidentsByAddress("Toulouse");

        // Verify the result
        Assertions.assertEquals(1, result.size());
    }

    //@Test
    public void testGetResidentsByStationNumber() {
        // Create mock repositories
        FirestationRepository firestationRepository = Mockito.mock(FirestationRepository.class);
        PersonRepository personRepository = Mockito.mock(PersonRepository.class);
        AllergyRepository allergyRepository = Mockito.mock(AllergyRepository.class);
        MedicalrecordRepository medicalrecordRepository = Mockito.mock(MedicalrecordRepository.class);
        PersonService personService = mock(PersonService.class);

        // Set up mock data
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("Toulouse", "1"));
        firestations.add(new Firestation("Paris", "2"));
        Mockito.when(firestationRepository.getFirestationByStation("1")).thenReturn(firestations.subList(0, 1));
        Mockito.when(firestationRepository.getFirestationByStation("2")).thenReturn(firestations.subList(1, 2));

        List<Person> persons = new ArrayList<>();
        persons.add( new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995"));
        persons.add(new Person("Jane", "Doe", "Paris", "Paris","75000","0766452210","jane.doe@example.com", "02/02/2001"));

        Mockito.when(personRepository.getAll()).thenReturn(persons);

        List<Allergy> allergies1 = new ArrayList<>();
        allergies1.add(new Allergy("peanuts"));
        List<Allergy> allergies2 = new ArrayList<>();
        allergies2.add(new Allergy("shellfish"));
        Mockito.when(allergyRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(allergies1);
        Mockito.when(allergyRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(allergies2);

        List<Medicalrecord> medicalrecords1 = new ArrayList<>();
        medicalrecords1.add(new Medicalrecord("John","Boyd", "Doliprane : 200mg", "16/03/1995"));
        List<Medicalrecord> medicalrecords2 = new ArrayList<>();
        medicalrecords2.add(new Medicalrecord("Jane","Doe", "Doliprane : 200mg", "02/02/1195"));
        Mockito.when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("John", "Doe")).thenReturn(medicalrecords1);
        Mockito.when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("Jane", "Doe")).thenReturn(medicalrecords2);

        // Call the method being tested
        Map<String, List<FloodDto>> result = personService.getResidentsByStationNumber(Arrays.asList("1", "2"));

        // Verify the result
        Assert.assertEquals(2, result.size());

        List<FloodDto> floodDataDtos1 = result.get("123 Main St");
        Assert.assertEquals(2, floodDataDtos1.size());
        Assert.assertEquals("John", floodDataDtos1.get(0).getFloodDataDto().getFirstName());
        Assert.assertEquals("Doe", floodDataDtos1.get(0).getFloodDataDto().getLastName());

    }
}

