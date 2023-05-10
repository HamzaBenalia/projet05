package com.safetyNet.alert.service;

import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.repository.AllergyRepository;
import com.safetyNet.alert.service.AllergyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AllergyServiceTest {
    @Test
    public void testAddAllergy() {
        // Créer une instance de la classe à tester et injecter le mock comme dépendance
        AllergyRepository allergyService = mock(AllergyRepository.class);


        // Créer une nouvelle allergie à ajouter
        Allergy newAllergy = new Allergy("John", "Doe", "Pollen");

        // Créer une allergie existante
        Allergy existingAllergy = new Allergy("Jane", "Doe", "Pollen");

        // Simuler le comportement du mock pour la méthode findByFirstNameLastNameAndAllergy()
        when(allergyService.findByFirstNameLastNameAndAllergy("John", "Doe", "Pollen")).thenReturn(newAllergy);
        when(allergyService.findByFirstNameLastNameAndAllergy("Jane", "Doe", "Pollen")).thenReturn(null);

        // Ajouter une nouvelle allergie
        allergyService.save(newAllergy);

        // Vérifier que l'allergie a été ajoutée en appelant la méthode save() du mock


        // Ajouter une allergie existante
        allergyService.save(existingAllergy);

        // Vérifier que l'allergie n'a pas été ajoutée en appelant la méthode save() du mock
        verify(allergyService, times(1)).save(existingAllergy);

    }

    @Test
    public void testGetAllAllergies() {
        // Créer une instance de la classe à tester

        // Créer une liste d'allergies
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(new Allergy("John", "Doe", "Pollen"));
        allergies.add(new Allergy("Jane", "Doe", "Peanuts"));


        AllergyService allergyService1 = mock(AllergyService.class);
        // Injecter la liste d'allergies dans la classe à tester
        when(allergyService1.getAll()).thenReturn(allergies);

        // Vérifier que la méthode getAll() retourne bien la liste d'allergies

        Assertions.assertEquals(allergies.get(0).getFirstName(), "John");
        Assertions.assertEquals(allergies.get(1).getLastName(), "Doe");
    }

    /*@Test
    public void testDeleteAllergyByFirstNameLastNameAndNameAllergy() {


        // Créer une liste d'allergies
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(new Allergy("John", "Doe", "Pollen"));
        allergies.add(new Allergy("Jane", "Doe", "Peanuts"));

        // Injecter la liste d'allergies dans la classe à tester
        AllergyService allergyService = mock(AllergyService.class);
        AllergyRepository allergyRepository = mock(AllergyRepository.class);

        when(allergyService.getAll()).thenReturn(allergies);
        when(allergyRepository.deleteAllergyByFirstNameLastNameAndAllergy("John", "Doe", "Pollen")).thenReturn(allergies);

        // Supprimer une allergie
         allergyRepository.deleteAllergyByFirstNameLastNameAndAllergy("John", "Doe", "Pollen");

        // Vérifier que l'allergie a bien été supprimée
        List<Allergy> result = allergyService.getAll();

        Assertions.assertEquals("Jane", result.get(0).getFirstName());
        Assertions.assertEquals("Doe", result.get(0).getLastName());
        Assertions.assertEquals("Peanuts", result.get(0).getNameAllergy());
    }
*/

}
