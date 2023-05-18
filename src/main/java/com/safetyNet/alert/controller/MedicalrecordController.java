package com.safetyNet.alert.controller;

import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.service.MedicalrecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalrecord")
@Slf4j
public class MedicalrecordController {

    @Autowired
    MedicalrecordService medicalrecordService;

    @PostMapping
    public void addMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
        log.info("Creation de la personne {}", medicalrecord);

        medicalrecordService.add(medicalrecord);
    }

    @GetMapping
    public List<Medicalrecord> getAll() {
        log.info("chercher tous les dossiers medicales ");
        return medicalrecordService.getAll();
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public void deleteMedicalrecord(@RequestBody Medicalrecord medicalrecord, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        log.info("Supprimer un dossier medicale par prenom = {} && par nom ={}", firstName, lastName);
        medicalrecordService.deleteMeicalrecordByFirstNameLastNameAndNamePosology(firstName, lastName, medicalrecord.getNamePosology());
    }

    @PutMapping("/{oldNamePosology}")
    public ResponseEntity<String> updateMedicalrecord(@RequestBody Medicalrecord updateMedicalrecord, @PathVariable("oldNamePosology") String oldNamePosology) {
        log.info("Mise à jour d'un dossier medical par lancien = {}", oldNamePosology);

        List<Medicalrecord> existingMedicalrecord = medicalrecordService.getMedicalrecorByFirstNameAndLastName(updateMedicalrecord.getFirstName(), updateMedicalrecord.getLastName());
        if (existingMedicalrecord == null) {
            return new ResponseEntity<>("Medicalrecord not found", HttpStatus.NOT_FOUND);
        }
        medicalrecordService.updateMedicalrecords(updateMedicalrecord, oldNamePosology);

        return new ResponseEntity<>("Medicalrecord updated successfully", HttpStatus.OK);

    }

    @GetMapping("/findByFirstNameLastNameAndPosology/{firstName}/{lastName}/{namePosology}")
    public Medicalrecord findByFirstNameLastNameAndPosology(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("namePosology") String namePosology) {
        log.info("Chercher des dossiers medicales par prénom = {} && par nom = {} ", firstName, lastName);
        return medicalrecordService.findByFirstNameLastNameAndPosology(firstName, lastName, namePosology);
    }
}

