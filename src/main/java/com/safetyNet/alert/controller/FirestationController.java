package com.safetyNet.alert.controller;

import com.safetyNet.alert.dto.stationDto.StationInfoDto;
import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.service.FirestationService;
import com.safetyNet.alert.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FirestationController {
    public static final String PATH = "/firestation";

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private PersonService personService;

    @PostMapping(PATH)
    public void addFirestation(@RequestBody Firestation firestation) {
        log.info("Creation de la personne {}", firestation);
        firestationService.add(firestation);
    }

    @GetMapping(PATH + "/all")
    public List<Firestation> getAllFirestation() {
        log.info("Recherche de toutes les stations");
        return firestationService.getAll();
    }

    @DeleteMapping(PATH + "/{address}")
    public List<Firestation> DeleteFirestation(@PathVariable("address") String firestation) {
        log.info("Supprimer une personne par firestation = {}", firestation);
        return firestationService.deleteFirestationByAddress(firestation);
    }

    @PutMapping(PATH)
    public ResponseEntity<String> updateFirestation(@RequestBody Firestation updateFirestation) {
        log.info("Mise à jour avec succée de = {}", updateFirestation);
        // Vérifier si la personne à mettre à jour existe
        Firestation existingFirestation = firestationService.getFirestationByAddress(updateFirestation.getAddress());
        if (existingFirestation == null) {
            return new ResponseEntity<>("Firestation not found", HttpStatus.NOT_FOUND);
        }
        firestationService.updateFirestation(updateFirestation);

        return new ResponseEntity<>("Firestation updated successfully", HttpStatus.OK);
    }

    @GetMapping(PATH)
    public StationInfoDto getPersonByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        log.info("Chercher les personnes couvertes par la caserne de pompiers par = {}", stationNumber);
        return personService.getPersonByFirestation(stationNumber);
    }

}



