package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Firestation;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Repository
public class FirestationRepository {
    private List<Firestation> firestations = new ArrayList<>();


    public void save(Firestation firestation) {
        firestations.add(firestation);
    }

    public List<Firestation> getAll() {
        return firestations;
    }

    public void deleteFirestation(Firestation firestation) {
        firestations.remove(firestation);
    }

    public void updateFirestation(Firestation updateFirestation) {
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(updateFirestation.getAddress())) {
                firestation.setStation(updateFirestation.getStation());
            }
        }

    }

    public List<String> getAdresseByStation(String stationNumber) {
        List<String> firestationList = new ArrayList<>();
        for (Firestation firestation : firestations) {
            if (firestation.getStation().equals(stationNumber)) {
                firestationList.add(firestation.getAddress());
            }
        }
        return firestationList;
    }

    public List<Firestation> getFirestationByStation(String stationNumber) {
        List<Firestation> firestationList = new ArrayList<>();
        for (Firestation firestation : firestations) {
            if (firestation.getStation().equals(stationNumber)) {
                firestationList.add(firestation);
            }
        }
        return firestationList;
    }

    public List<String> getStationByAddress(String address) {
        List<String> firestationList = new ArrayList<>();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                firestationList.add(firestation.getStation());
            }
        }
        return firestationList;
    }

    @Test
    public void testGetStationByAddress() {
        FirestationRepository firestationRepository = new FirestationRepository();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "2");
        Firestation firestation3 = new Firestation("789 Elm Rd", "1");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<String> result = firestationRepository.getStationByAddress("123 Main St");
        List<String> expected = Arrays.asList("1");
        assertEquals(expected, result);
    }


}