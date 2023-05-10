package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.repository.AllergyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AllergyRepositoryTest {

        @Test
        public void testSave() {
            Allergy allergy = new Allergy("Peanuts");
            AllergyRepository allergyRepository = new AllergyRepository();

            // Save the allergy
            allergyRepository.save(allergy);

            // Ensure that the allergy was added to the list
            Assertions.assertTrue(allergyRepository.getAll().contains(allergy));
        }


        @Test
        public void testFindByFirstNameLastNameAndAllergy() {
            AllergyRepository allergyRepository = new AllergyRepository();
            allergyRepository.save(new Allergy("John", "Doe", "Peanuts"));
            allergyRepository.save(new Allergy("Jane", "Doe", "Gluten"));

            // Test finding an allergy that exists
            Allergy foundAllergy = allergyRepository.findByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts");
            Assertions.assertNotNull(foundAllergy);
            Assertions.assertEquals("John", foundAllergy.getFirstName());
            Assertions.assertEquals("Doe", foundAllergy.getLastName());
            Assertions.assertEquals("Peanuts", foundAllergy.getNameAllergy());

            // Test finding an allergy that doesn't exist
            foundAllergy = allergyRepository.findByFirstNameLastNameAndAllergy("Bob", "Smith", "Eggs");
            Assertions.assertNull(foundAllergy);
        }

    @Test
    public void testGetAll() {
        AllergyRepository allergyDAO = new AllergyRepository();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));

        // Test getting all allergies
        List<Allergy> allAllergies = allergyDAO.getAll();
        Assertions.assertEquals(2, allAllergies.size());
    }

    @Test
    public void testDeleteAllergyByFirstNameLastNameAndAllergy() {
        AllergyRepository allergyDAO = new AllergyRepository();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));

        // Test deleting an allergy that exists
        List<Allergy> updatedAllergies = allergyDAO.deleteAllergyByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts");
        Assertions.assertEquals(1, updatedAllergies.size());
        Assertions.assertNull(allergyDAO.findByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts"));

        // Test deleting an allergy that doesn't exist
        updatedAllergies = allergyDAO.deleteAllergyByFirstNameLastNameAndAllergy("Bob", "Smith", "Eggs");
        Assertions.assertEquals(1, updatedAllergies.size());
    }

    @Test
    public void testUpdateAllergy() {
        AllergyRepository allergyDAO = new AllergyRepository();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));

        // Test updating an allergy that exists
        Allergy updatedAllergy = new Allergy("John", "Doe", "Shellfish");
        allergyDAO.updateAllergy(updatedAllergy, "Peanuts");
        Assertions.assertEquals("Shellfish", allergyDAO.findByFirstNameLastNameAndAllergy("John", "Doe", "Shellfish").getNameAllergy());
        Assertions.assertNull(allergyDAO.findByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts"));

        // Test updating an allergy that doesn't exist
        updatedAllergy = new Allergy("Bob", "Smith", "Eggs");
        allergyDAO.updateAllergy(updatedAllergy, "Peanuts");
        Assertions.assertNull(allergyDAO.findByFirstNameLastNameAndAllergy("Bob", "Smith", "Eggs"));
    }
    @Test
    public void testFindByFirstNameAndLastName() {
        AllergyRepository allergyDAO = new AllergyRepository();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));
        allergyDAO.save(new Allergy("John", "Doe", "Shellfish"));

        // Test finding allergies for a first name and last name that exist
        List<Allergy> johnDoeAllergies = allergyDAO.findByFirstNameAndLastName("John", "Doe");
        Assertions.assertEquals(2, johnDoeAllergies.size());
        Assertions.assertTrue(johnDoeAllergies.stream().anyMatch(allergy -> allergy.getNameAllergy().equals("Peanuts")));
        Assertions.assertTrue(johnDoeAllergies.stream().anyMatch(allergy -> allergy.getNameAllergy().equals("Shellfish")));

        // Test finding allergies for a first name and last name that don't exist
        List<Allergy> bobSmithAllergies = allergyDAO.findByFirstNameAndLastName("Bob", "Smith");
        Assertions.assertTrue(bobSmithAllergies.isEmpty());
    }


    }





