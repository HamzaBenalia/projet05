package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonRepositoryTest {


    private final List<Person> person1 = new ArrayList<>();

    private PersonRepository personRepository;


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


   /* @Test
    public void testUpdatePerson() {
        // Créer quelques personnes pour le test
        Person person1 = new Person("Alice", "Smith", "123 Main St", "Toulouse", "31000", "0766764625", "alice@gmail.com", "15/02/1995");
        Person person2 = new Person("Hamza", "Ben", "321 Main St", "Paris", "Toulouse", "0766552200", "Hamza@gmail.com", "18/02/1999");
        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

        // Mettre à jour une personne existante
        Person updatedPerson = new Person("Alice", "Smith", "123 Main St", "Montpellier", "34000", "0766764625", "alice@gmail.com", "15/02/1995");

        when(personRepository.updatePerson(updatedPerson)).thenReturn();
        // Vérifier que la personne a été mise à jour correctement
        List<Person> updatedPersons = personRepository.getAll();
        assertTrue(updatedPersons.contains("Montpellier"));

        // Vérifier que la deuxième personne n'a pas été modifiée
        assertEquals("Paris", updatedPersons.get(1).getCity());
    }
*/

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

