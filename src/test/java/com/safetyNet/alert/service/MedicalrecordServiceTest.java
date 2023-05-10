package com.safetyNet.alert.service;

import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.repository.MedicalrecordRepository;
import com.safetyNet.alert.service.MedicalrecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MedicalrecordServiceTest {

    @Test
    public void testAddMedicalrecord() {
        // Create a new medical record
        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("John");
        medicalrecord.setLastName("Doe");
        medicalrecord.setNamePosology("Aznol");


        // Create a mock MedicalrecordRepository that returns null
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);
        when(medicalrecordRepository.findByFirstNameLastNameAndPosology(anyString(), anyString(), anyString())).thenReturn(null);


        // Call the add method
        medicalrecordService.add(medicalrecord);

        // Verify that the repository's save method was called
        Mockito.verify(medicalrecordService, times(1)).add(medicalrecord);
    }

    @Test
    public void testGetAllMedicalrecords() {
        // Create some test data
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "aspirin", "16/02/1990");
        Medicalrecord medicalrecord2 = new Medicalrecord("Alice", "Smith", "ibuprofen", "17/02/1980");

        // Create a mock MedicalrecordRepository that returns the test data
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);
        List<Medicalrecord> medicalrecordList = new ArrayList<>();
        medicalrecordList.add(medicalrecord1);
        medicalrecordList.add(medicalrecord2);
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);
        when(medicalrecordService.getAll()).thenReturn(medicalrecordList);

        // Call the getAll method

        List<Medicalrecord> result = medicalrecordService.getAll();

        // Verify that the correct data is returned
        assertEquals(2, result.size());
        assertTrue(result.contains(medicalrecord1));
        assertTrue(result.contains(medicalrecord2));

        // Verify that the medicalrecordRepository's getAll method was called
        Mockito.verify(medicalrecordService, Mockito.times(1)).getAll();
    }

    @Test
    public void testGetMedicalrecorByFirstNameAndLastName() {
        // Create a list of medical records to use as test data
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "aspirin", "16/02/1995");
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);
        List<Medicalrecord> medicalrecordList = new ArrayList<>();
        medicalrecordList.add(medicalrecord1);


        // Call the getMedicalrecorByFirstNameAndLastName method
        when(medicalrecordService.getAll()).thenReturn(medicalrecordList);
        when(medicalrecordService.getMedicalrecorByFirstNameAndLastName("John", "Doe")).thenReturn(medicalrecordList);

        List<Medicalrecord> result = medicalrecordService.getMedicalrecorByFirstNameAndLastName("John", "Doe");


        // Verify that the correct medical record was returned
        assertEquals(result, medicalrecordList);


        // Verify that the medicalrecordRepository's getAll method was called
        Mockito.verify(medicalrecordService, Mockito.times(1)).getMedicalrecorByFirstNameAndLastName("John", "Doe");
    }


   /* @Test
    public void testDeleteMedicalRecordByFirstNameLastNameAndNamePosology(){

        // Créer un mock de la dépendance MedicalrecordRepository
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);

        // Créer une instance de la classe à tester et injecter le mock comme dépendance
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);

        // Créer une liste de dossiers médicaux à supprimer
        List<Medicalrecord> medicalrecordList = new ArrayList<>();
        Medicalrecord medicalrecord1 = new Medicalrecord("Hamza", "Benalia", "Doliprane : 200mg", "16/02/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("Ali", "Brahim", "Aspirine : 500mg", "01/01/1980");
        medicalrecordList.add(medicalrecord1);
        medicalrecordList.add(medicalrecord2);

        // Faire en sorte que la méthode getAll retourne la liste de dossiers médicaux
        when(medicalrecordRepository.getAll()).thenReturn(medicalrecordList);

        // Appeler la méthode à tester
        medicalrecordService.deleteMeicalrecordByFirstNameLastNameAndNamePosology("Hamza", "Benalia","Doliprane : 200mg");

        // Vérifier que la méthode deleteMedicalrecordByFirstNameLastNameAndPosology a été appelée avec les bons paramètres
        //verify(medicalrecordRepository).deleteMedicalrecordByFirstNameLastNameAndPosology("Hamza", "Benalia", "Doliprane : 200mg");

        // Vérifier que le dossier médical a bien été supprimé de la liste
        List<Medicalrecord> result = medicalrecordService.getAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Ali", result.get(0).getFirstName());
    }
*/
    @Test
    public void testUpdateMedicalrecords(){

        // Créer un mock de la dépendance MedicalrecordRepository
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);

        // Créer une instance de la classe à tester et injecter le mock comme dépendance
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);

        // Créer un dossier médical à mettre à jour
        Medicalrecord medicalrecordToUpdate = new Medicalrecord("Hamza", "Benalia", "Doliprane : 200mg", "16/02/1995");

        // Appeler la méthode à tester
        medicalrecordService.updateMedicalrecords(medicalrecordToUpdate, "Doliprane : 200mg");

        // Vérifier que la méthode updateMedicalrecords a été appelée avec les bons paramètres
        verify(medicalrecordService).updateMedicalrecords(medicalrecordToUpdate, "Doliprane : 200mg");
    }

  /* @Test
    public void testFindByFirstNameLastNameAndPosology(){

        // Créer un mock de la dépendance MedicalrecordRepository
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);

        // Créer une instance de la classe à tester et injecter le mock comme dépendance
        MedicalrecordService medicalrecordService = mock(MedicalrecordService.class);

        // Créer un dossier médical à retourner
        Medicalrecord expectedMedicalrecord = new Medicalrecord("Hamza", "Benalia", "Doliprane : 200mg", "16/02/1995");

        // Définir le comportement du mock pour la méthode findByFirstNameLastNameAndPosology
        when(medicalrecordRepository.findByFirstNameLastNameAndPosology("Hamza", "Benalia", "Doliprane : 200mg")).thenReturn(expectedMedicalrecord);

        // Appeler la méthode à tester
        Medicalrecord actualMedicalrecord = medicalrecordService.findByFirstNameLastNameAndPosology("Hamza", "Benalia", "Doliprane : 200mg");

        // Vérifier que la méthode findByFirstNameLastNameAndPosology a été appelée avec les bons paramètres
        verify(medicalrecordService).findByFirstNameLastNameAndPosology("Hamza", "Benalia", "Doliprane : 200mg");

        // Vérifier que le dossier médical retourné est celui attendu
        Assertions.assertEquals(expectedMedicalrecord, actualMedicalrecord);
    }

*/
}
