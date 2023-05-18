package com.safetyNet.alert.service;

import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.repository.MedicalrecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalrecordService {

    @Autowired
    MedicalrecordRepository medicalrecordRepository;

    public void add(Medicalrecord medicalrecord) {
        if (medicalrecordRepository.findByFirstNameLastNameAndPosology(medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getNamePosology()) != null) {
            return;
        }
        medicalrecordRepository.save(medicalrecord);
    }

    public List<Medicalrecord> getAll() {
        return medicalrecordRepository.getAll();
    }

    public List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName) {
        return medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(firstName, lastName);
    }


    public void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String namePosology) {
        medicalrecordRepository.deleteMedicalrecordByFirstNameLastNameAndPosology(fistName, lastName, namePosology);
    }

    public void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology) {
        medicalrecordRepository.updateMedicalrecords(updateMedicalrecord, oldNamePosology);
    }

    public Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology) {
        return medicalrecordRepository.findByFirstNameLastNameAndPosology(firstName, lastName, namePosology);
    }
}