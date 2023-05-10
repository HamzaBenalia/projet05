package com.safetyNet.alert.service;

import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;

    public void add(Firestation firestation) {
        if (getFirestationByAddress(firestation.getAddress()) != null) {
            return;
        }
        firestationRepository.save(firestation);

    }

    public List<Firestation> getAll() {
        return firestationRepository.getAll();
    }

    public Firestation getFirestationByAddress(String address) {
        List<Firestation> firestations = firestationRepository.getAll();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }

        return null;
    }


    /*public List<Firestation> deleteFirestationByAddress(String address) {
        List<Firestation> firestations = firestationRepository.getAll();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                firestations.remove(firestation);
                return firestations;
            }
        }return null;
    }*/


    public List<Firestation> deleteFirestationByAddress(String address) {
        List<Firestation> firestations = firestationRepository.getAll();
        Iterator<Firestation> iterator = firestations.iterator();
        while (iterator.hasNext()) {
            Firestation firestation = iterator.next();
            if (firestation.getAddress().equals(address)) {
                iterator.remove();
                return firestations;
            }
        }
        return null;
    }

    public void updateFirestation(Firestation updateFirestation) {
        firestationRepository.updateFirestation(updateFirestation);
    }

    public List<String> getFirestationAddressByStation(String stationNumber) {
        return firestationRepository.getAdresseByStation(stationNumber);
    }


}