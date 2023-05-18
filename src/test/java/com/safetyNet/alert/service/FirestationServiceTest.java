package com.safetyNet.alert.service;

import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.repository.FirestationRepository;
import com.safetyNet.alert.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
    @Captor
    ArgumentCaptor<Firestation> firestationArgumentCaptor;
    @InjectMocks
    FirestationService firestationService;
    @Mock
    private FirestationRepository firestationRepository;
    @Mock
    private PersonRepository personRepository;

    @Test
    public void testAddFirestation() {
        Firestation firestation = new Firestation("Toulouse", "1");
        when(firestationRepository.getAll()).thenReturn(new ArrayList<>());

        firestationService.add(firestation);

        Mockito.verify(firestationRepository, times(1)).save(firestationArgumentCaptor.capture());

        assertThat(firestationArgumentCaptor.getValue())
                .extracting("address", "station")
                .containsExactly(firestation.getAddress(), firestation.getStation());

    }


    @Test
    public void testGetAll() {
        // Create a list of fire stations to use as test data
        Firestation firestation1 = new Firestation("Toulouse", "1");
        Firestation firestation2 = new Firestation("Montpellier", "2");
        List<Firestation> allFirestations = Arrays.asList(firestation1, firestation2);

        when(firestationRepository.getAll()).thenReturn(allFirestations);

        List<Firestation> result = firestationService.getAll();
        Mockito.verify(firestationRepository, times(1)).getAll();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(firestation1));
        Assertions.assertTrue(result.contains(firestation2));
    }


    @Test
    public void testGetFirestationByAddress() {
        // Create test data
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Elm St", "2");

        List<Firestation> firestationList = new ArrayList<>();

        firestationList.add(firestation1);
        firestationList.add(firestation2);

        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);


        when(firestationService.getAll()).thenReturn(firestationList);

        Firestation result1 = firestationService.getFirestationByAddress("123 Main St");
        Firestation result2 = firestationService.getFirestationByAddress("13 route LosAngeles");

        assertNotNull(result1);
        assertNull(result2);
        assertEquals("1", result1.getStation());
    }


    @Test
    public void testDeleteFirestationByAddress() {
        // Create a list of firestations to use as test data
        Firestation firestation1 = new Firestation("1st Avenue", "1");
        Firestation firestation2 = new Firestation("2nd Avenue", "2");

        // Add the test data to the repository
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation1);
        firestationList.add(firestation2);
        when(firestationRepository.getAll()).thenReturn(firestationList);

        firestationService.deleteFirestationByAddress("1st Avenue");


        assertEquals(1, firestationList.size());
    }

}
