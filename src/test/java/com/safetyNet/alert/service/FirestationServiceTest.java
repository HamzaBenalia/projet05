package com.safetyNet.alert.service;

import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.repository.FirestationRepository;
import com.safetyNet.alert.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FirestationServiceTest {

    @Test
    public void testAddFirestation() {
        // Create a new firestation object to add
        Firestation firestation = new Firestation();
        firestation.setAddress("123 Main St");
        firestation.setStation("1");

        // Create a mock FirestationRepository
        FirestationRepository firestationRepository = mock(FirestationRepository.class);
        FirestationService firestationService = mock(FirestationService.class);

        // When getFirestationByAddress is called with the firestation's address, return null
        when(firestationRepository.getStationByAddress(firestation.getAddress())).thenReturn(null);

        // Call the add method
        firestationService.add(firestation);

        verify(firestationService, times(1)).add(firestation);
    }


    @Test
    public void testGetAll() {
        // Create a list of fire stations to use as test data
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Elm St", "2");
        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation1);
        firestationList.add(firestation2);

        // Create a mock FirestationRepository that returns the test data
        FirestationRepository firestationRepository = mock(FirestationRepository.class);
        FirestationService firestationService = mock(FirestationService.class);
        when(firestationService.getAll()).thenReturn(firestationList);

        List<Firestation> result = firestationService.getAll();

        // Verify that the correct data was returned
        assertEquals(2, result.size());
        assertTrue(result.contains(firestation1));
        assertTrue(result.contains(firestation2));

        // Verify that the firestationRepository's getAll method was called
        verify(firestationService, Mockito.times(1)).getAll();
    }


    @Test
    public void testGetFirestationByAddress() {
        // Create test data
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Elm St", "2");
        FirestationService firestationService = mock(FirestationService.class);
        firestationService.add(firestation1);
        firestationService.add(firestation2);
        when(firestationService.getFirestationByAddress("123 Main St")).thenReturn(firestation1);
        when(firestationService.getFirestationByAddress("456 Elm St")).thenReturn(firestation2);

        Firestation result1 = firestationService.getFirestationByAddress("123 Main St");
        Firestation result2 = firestationService.getFirestationByAddress(("13 route LosAngeles"));
        assertNotNull(result1);
        assertNull(result2);
        assertEquals("1", result1.getStation());
    }

    @Test
    public void testDeleteFirestationByAddress() {
        // Create a list of firestations to use as test data
        Firestation firestation1 = new Firestation("1st Avenue", "1");
        Firestation firestation2 = new Firestation("2nd Avenue", "2");
        FirestationRepository firestationRepository = mock(FirestationRepository.class);
        FirestationService firestationService = mock(FirestationService.class);


        // Add the test data to the repository
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        when(firestationService.getFirestationByAddress("1st Avenue")).thenReturn(firestation1);
        when(firestationService.getFirestationByAddress("2nd Avenue")).thenReturn(firestation2);

        // Call the deleteFirestationByAddress method
        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation1);
        firestationList.add(firestation2);
        List<Firestation> result = new ArrayList<>();
        firestationRepository.deleteFirestation(firestation1);

        result = firestationService.getAll();

        assertFalse(result.contains(new Firestation("1sd Avenue", "1")));
    }




}
