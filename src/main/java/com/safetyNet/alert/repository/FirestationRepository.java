package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationRepository {
    private final List<Firestation> firestations = new ArrayList<>();


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

}