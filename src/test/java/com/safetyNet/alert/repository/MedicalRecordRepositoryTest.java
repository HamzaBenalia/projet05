package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.repository.MedicalrecordRepository;
import com.safetyNet.alert.service.MedicalrecordService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MedicalRecordRepositoryTest {

    @Test
    public void saveTest() {
        MedicalrecordRepository medicalRecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalRecord = new Medicalrecord("John", "Boyd", "aznol","13/02/1985");
        medicalRecordRepository.save(medicalRecord);
        assertTrue(medicalRecordRepository.getAll().contains(medicalRecord));
    }
    @Test
    public void testFindByFirstNameLastNameAndPosology() {
        MedicalrecordRepository medicalRecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "aznol:350mg","16/02/1995");
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
   /* @Test
    public void testGetMedicalrecordByFirstNameAndLastName() {
        MedicalrecordRepository medicalrecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "Aznol:350mg","16/02/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("John", "Mark", "Doliprane", "02/02/2000");
        Medicalrecord medicalrecord3 = new Medicalrecord("Jane", "Doe", "Amoxiciline", "03/03/1995");
        medicalrecordRepository.save(medicalrecord1);
        medicalrecordRepository.save(medicalrecord2);
        medicalrecordRepository.save(medicalrecord3);

        List<Medicalrecord> result = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("John", "Boyd");

        assertEquals(2, result.size());
        assertTrue(result.contains(medicalrecord1));
        assertTrue(result.contains(medicalrecord2));
    }*/

    @Test
    public void testDeleteMedicalrecordByFirstNameLastNameAndPosology() {
        MedicalrecordRepository medicalRecordRepository = new MedicalrecordRepository();
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Boyd", "Aznol:350mg","16/02/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("John", "Mark", "Doliprane", "02/02/2000");
        medicalRecordRepository.save(medicalrecord1);
        medicalRecordRepository.save(medicalrecord2);


        List<Medicalrecord> result = medicalRecordRepository.deleteMedicalrecordByFirstNameLastNameAndPosology(
                "John", "Mark", "Doliprane");
        List<Medicalrecord> expected = Arrays.asList(medicalrecord1);
        assertEquals(expected, result);

        // Verify that the deleted medical record is no longer in the repository
        assertNull(medicalRecordRepository.findByFirstNameLastNameAndPosology("John", "Mark", "Doliprane"));
    }

    /*@Test
    public void testUpdateMedicalrecords() {
        // Create test data
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "Doliprane","16/02/1995" );
        Medicalrecord medicalrecord2 = new Medicalrecord("Jane", "Doe", "Ubiprophen","15/05/1995");
        List<Medicalrecord> medicalrecords = Arrays.asList(medicalrecord1, medicalrecord2);

        // Create service and call the method being tested
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);
        Medicalrecord updatedMedicalrecord = new Medicalrecord("John", "Doe", "Amoxiciline", "16/02/1995");
        medicalrecordRepository.updateMedicalrecords(updatedMedicalrecord, "Doliprane");

        // Verify that the old medical record is removed and the new one is added
        assertNull(medicalrecordRepository.findByFirstNameLastNameAndPosology("John", "Doe", "Dpliprane"));
        assertEquals("Amoxiciline", medicalrecord1.getNamePosology());
    }*/

}
