package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Firestation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FirestationRepositoryTest {

    @Test
    public void testSaveFirestation() {
        // Création d'un objet FirestationRepository
        FirestationRepository firestationRepository = new FirestationRepository();

        // Création d'une Firestation à enregistrer
        Firestation firestation = new Firestation("1", "123 Main St");

        // Enregistrement de la Firestation
        firestationRepository.save(firestation);

        // Vérification que la Firestation est bien enregistrée
        assertTrue(firestationRepository.getAll().contains(firestation));
    }

    @Test
    public void testGetAll() {
        // Création de deux objets Firestation
        Firestation firestation1 = new Firestation("1", "address1");
        Firestation firestation2 = new Firestation("2", "address2");

        // Ajout des objets à la liste de la FirestationRepository
        FirestationRepository firestationRepository = new FirestationRepository();
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        // Vérification que la liste retournée par getAll() contient bien les deux objets créés
        List<Firestation> firestations = firestationRepository.getAll();
        assertEquals(2, firestations.size());
        assertTrue(firestations.contains(firestation1));
        assertTrue(firestations.contains(firestation2));
    }

    @Test
    public void deleteFirestationTest() {
        // création de l'objet à tester
        FirestationRepository firestationRepository = new FirestationRepository();

        // création de deux casernes de pompiers
        Firestation firestation1 = new Firestation("1", "1st street");
        Firestation firestation2 = new Firestation("2", "2nd street");

        // ajout des casernes à la liste
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        // suppression de la première caserne
        firestationRepository.deleteFirestation(firestation1);

        // vérification que la première caserne a été supprimée et que la deuxième caserne est toujours présente
        List<Firestation> firestations = firestationRepository.getAll();
        assertFalse(firestations.contains(firestation1));
        assertTrue(firestations.contains(firestation2));
    }

    @Test
    public void testUpdateFirestation() {
        // Créer une instance de FirestationRepository et ajouter une firestation
        FirestationRepository firestationRepository = new FirestationRepository();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        firestationRepository.save(firestation1);

        // Créer une nouvelle firestation avec la même adresse que la première, mais avec un numéro de station différent
        Firestation updatedFirestation = new Firestation("123 Main St", "2");

        // Mettre à jour la firestation dans le repository
        firestationRepository.updateFirestation(updatedFirestation);

        // Vérifier que la firestation a bien été mise à jour
        List<Firestation> firestations = firestationRepository.getAll();
        assertEquals(1, firestations.size());
        assertEquals(updatedFirestation.getStation(), firestations.get(0).getStation());
    }

    @Test
    public void testGetAdresseByStation() {
        FirestationRepository firestationRepository = new FirestationRepository();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "1");
        Firestation firestation3 = new Firestation("789 Elm Rd", "2");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<String> result = firestationRepository.getAdresseByStation("1");
        List<String> expected = Arrays.asList("123 Main St", "456 Oak Ave");
        assertEquals(expected, result);
    }

    @Test
    public void testGetFirestationByStation() {
        FirestationRepository firestationRepository = new FirestationRepository();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "2");
        Firestation firestation3 = new Firestation("789 Elm Rd", "3");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<Firestation> result = firestationRepository.getFirestationByStation("1");
        List<Firestation> expected = List.of(firestation1);
        assertEquals(expected, result);
    }

    @Test
    public void testgetStationByAddress() {
        FirestationRepository firestationRepository = new FirestationRepository();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "1");
        Firestation firestation3 = new Firestation("789 Elm Rd", "2");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<String> result = firestationRepository.getStationByAddress("123 Main St");
        List<String> expected = List.of("1");
        assertEquals(expected, result);
    }

}
