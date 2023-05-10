package com.safetyNet.alert.repository;
import com.safetyNet.alert.model.Person;
import com.safetyNet.alert.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonRepositoryTest {



    private List<Person> person1 = new ArrayList<>();


    @Test
    public void SaveTest() {
        PersonRepository personRepository = new PersonRepository();
        Person person1 = new Person("John", "Boyd");
        personRepository.save(person1);
        assertTrue(personRepository.getAll().contains(person1));
    }

    @Test
    public void getAllTest() {
        PersonRepository personRepository = new PersonRepository();
        Person person1 = new Person("John", "Boyd");
        Person person2 = new Person("Jacob", "Boyd");
        personRepository.save(person1);
        personRepository.save(person2);
        List<Person> allPersons = personRepository.getAll();
        assertEquals(2, allPersons.size());
        assertTrue(allPersons.contains(person1));
        assertTrue(allPersons.contains(person2));
    }

    @Test
    public void deletePersonTest() {
        PersonRepository personRepository = new PersonRepository();
        Person person1 = new Person("John", "Boyd");
        Person person2 = new Person("Jane", "Doe");
        personRepository.save(person1);
        personRepository.save(person2);

        // Vérifie que les deux personnes ont été ajoutées à la liste persons
        assertEquals(2, personRepository.getAll().size());

        // Supprime person1
        personRepository.deletePerson(person1);

        // Vérifie que person1 a été supprimé et que la taille de la liste est réduite de 1
        assertFalse(personRepository.getAll().contains(person1));
        assertEquals(1, personRepository.getAll().size());
    }

   @Test
    public void updatePersonTest() {
        PersonRepository personRepository = new PersonRepository();
        Person person1 = new Person("John", "Boyd","789 Maple St");
        Person person2 = new Person("Mary", "Smith");
        personRepository.save(person1);
        personRepository.save(person2);

        // Create an updated version of person1
        Person updatedPerson1 = new Person("Hamza", "Boyd","Toulouse");

        // Update person1 in the repository
        personRepository.updatePerson(updatedPerson1);

        // Verify that person1 was updated in the repository
        List<Person> allPersons = personRepository.getAll();
        for (Person person : allPersons) {
            if (person.getFirstName().equals("Hamza") && person.getLastName().equals("Boyd")) {
                Assertions.assertEquals("Hamza", person.getFirstName());
                Assertions.assertEquals("Toulouse", person.getAddress());

            }
        }

        // Verify that person2 was not affected by the update
        assertTrue(allPersons.contains(person2));
    }

    @Test
    public void testGetPersonByName() {
        PersonRepository personRepository = new PersonRepository();
        Person person1 = new Person("John", "Boyd");
        personRepository.save(person1);

        List<Person> result = personRepository.getPersonByName("Boyd");
        assertEquals(1, result.size());
        assertEquals(person1, result.get(0));

        List<Person> emptyResult = personRepository.getPersonByName("Unknown");
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void testGetPersonByAddress() {
        // Créer un repository avec des personnes
        PersonRepository personRepository = new PersonRepository();
        Person person1 = new Person("John", "Boyd", "1509 Culver St");
        Person person2 = new Person("Foster", "Shepard", "748 Townings Dr");
        Person person3 = new Person("Jacob", "Boyd", "1509 Culver St");
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);

        // Vérifier que la méthode retourne les personnes attendues
        List<Person> personsByAddress = personRepository.getPersonByAddress("1509 Culver St");
        Assertions.assertEquals(2, personsByAddress.size());
        Assertions.assertTrue(personsByAddress.contains(person1));
        Assertions.assertTrue(personsByAddress.contains(person3));
    }


    @Test
    public void testGetPersonByPhone() {
        // Create a new PersonRepository
        PersonRepository personRepository = new PersonRepository();

        // Add some test data
        Person person1 = new Person("John", "Boyd");
        person1.setPhone("555-1234");
        Person person2 = new Person("Jane", "Doe");
        person2.setPhone("555-5678");
        personRepository.save(person1);
        personRepository.save(person2);

        // Test with a valid phone number
        List<Person> result1 = personRepository.getPersonByPhone("555-1234");
        assertEquals(1, result1.size());
        assertTrue(result1.contains(person1));

        // Test with an invalid phone number
        List<Person> result2 = personRepository.getPersonByPhone("555-9999");
        assertEquals(0, result2.size());
    }
}

