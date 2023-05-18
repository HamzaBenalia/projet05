package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Medicalrecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordRepositoryTest {



    @Test
    public void saveTest() {
        MedicalrecordRepository medicalRecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalRecord = new Medicalrecord("John", "Boyd", "aznol", "13/02/1985");
        medicalRecordRepository.save(medicalRecord);
        assertTrue(medicalRecordRepository.getAll().contains(medicalRecord));
    }

    @Test
    public void testFindByFirstNameLastNameAndPosology() {
        MedicalrecordRepository medicalRecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "aznol:350mg", "16/02/1995");
        medicalRecordRepository.save(medicalrecord1);

        Medicalrecord result = medicalRecordRepository.findByFirstNameLastNameAndPosology("John", "Boyd", "aznol:350mg");
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Boyd", result.getLastName());
        assertEquals("aznol:350mg", result.getNamePosology());
    }

    @Test
    public void testGetAll() {
        // Arrange
        MedicalrecordRepository medicalrecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "Aznol:350mg", "1602/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("Jacob", "Boyd", "Doliprane:500mg", "01/01/1970");
        medicalrecordRepository.save(medicalrecord1);
        medicalrecordRepository.save(medicalrecord2);

        // Act
        List<Medicalrecord> medicalrecords = medicalrecordRepository.getAll();

        // Assert
        assertEquals(2, medicalrecords.size());
        assertTrue(medicalrecords.contains(medicalrecord1));
        assertTrue(medicalrecords.contains(medicalrecord2));
    }

    @Test
    public void testGetMedicalrecordByFirstNameAndLastName() {
        MedicalrecordRepository medicalrecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "Aznol:350mg", "16/02/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("John", "Mark", "Doliprane", "02/02/2000");
        Medicalrecord medicalrecord3 = new Medicalrecord("Jane", "Doe", "Amoxiciline", "03/03/1995");
        medicalrecordRepository.save(medicalrecord1);
        medicalrecordRepository.save(medicalrecord2);
        medicalrecordRepository.save(medicalrecord3);

        List<Medicalrecord> result = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("John", "Boyd");

        assertEquals(1, result.size());
        assertTrue(result.contains(medicalrecord1));
        assertFalse(result.contains(medicalrecord2));
    }

    @Test
    public void testDeleteMedicalrecordByFirstNameLastNameAndPosology() {
        MedicalrecordRepository medicalRecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "Aznol:350mg", "16/02/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("John", "Mark", "Doliprane", "02/02/2000");
        medicalRecordRepository.save(medicalrecord1);
        medicalRecordRepository.save(medicalrecord2);

        medicalRecordRepository.deleteMedicalrecordByFirstNameLastNameAndPosology(
                "John", "Mark", "Doliprane");
        // Verify that the deleted medical record is no longer in the repository
        Medicalrecord medicalrecordResult = medicalRecordRepository.getAll().get(0);
        assertEquals(medicalrecord1.getFirstName(), medicalrecordResult.getFirstName());
        assertEquals(medicalrecord1.getLastName(), medicalrecordResult.getLastName());
        assertEquals(medicalrecord1.getNamePosology(), medicalrecordResult.getNamePosology());
        assertEquals(medicalrecord1.getBirthDate(), medicalrecordResult.getBirthDate());
    }

    /*@Test
    public void testUpdateMedicalrecords() {
        // Créer une instance de FirestationRepository et ajouter une firestation
        MedicalrecordRepository medicalrecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord = new Medicalrecord("John","Doe","Doliprane : 200mg","10/02/1995");
        medicalrecordRepository.save(medicalrecord);

        Medicalrecord updatedMedicalrecord = new Medicalrecord("John", "Doe","Amoxiciline : 200mg","10/02/1995");



        // Mettre à jour la firestation dans le repository
        medicalrecordRepository.updateMedicalrecords(updatedMedicalrecord,"Doliprane");

        when(medicalrecordRepository.updateMedicalrecords(updatedMedicalrecord,"Doliprane : 200mg")).thenReturn(medicalrecord);

        // Vérifier que la firestation a bien été mise à jour
        List<Medicalrecord> medicalrecords = medicalrecordRepository.getAll();
        assertEquals(1, medicalrecords.size());
        assertEquals(updatedMedicalrecord.getFirstName(), medicalrecord.getFirstName());


    }
*/
}
