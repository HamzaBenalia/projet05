package com.safetyNet.alert.controller;

import com.safetyNet.alert.dto.childAlert.ChildDto;
import com.safetyNet.alert.dto.fire.FireDto;
import com.safetyNet.alert.dto.flood.FloodDto;
import com.safetyNet.alert.dto.personInfo.PersonInfoDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDto;
import com.safetyNet.alert.model.Person;
import com.safetyNet.alert.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
public class PersonController {
    public static final String PATH = "/person";
    @Autowired
    private PersonService personService;

    @PostMapping(PATH)
    public void addPerson(@RequestBody Person person) {
        log.info("Creation de la personne {}", person);
        personService.add(person);
    }

    @GetMapping(PATH)
    public List<Person> getAllPerson() {
        log.info("Recherche de toutes les personnes");
        return personService.getAll();
    }

    @DeleteMapping(PATH + "/{firstname}/{lastname}")
    public void deletePerson(@PathVariable("firstname") String firstName, @PathVariable("lastname") String lastName) {
        log.info("Supprimer une personne par nom ={} et prénom = {}", firstName, lastName);
        personService.deleteByFirstNameAndLastName(firstName, lastName);
    }

    @PutMapping(PATH)
    public ResponseEntity<String> updatePerson(@RequestBody Person updatedPerson) {

        log.info("Mise à jour avec succée de = {}", updatedPerson);
        // Vérifier si la personne à mettre à jour existe
        Person existingPerson = personService.getPersonByFirstNameAndLastName(updatedPerson.getFirstName(), updatedPerson.getLastName());
        if (existingPerson == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        personService.updatePerson(updatedPerson);
        return new ResponseEntity<>("Person updated successfully", HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDto> getMedicamentByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {

        log.info("Chercher les personnes avec leurs dossier medical par nom = {} et prenom = {} ", firstName, lastName);
        return personService.getPersonInfos(firstName, lastName);
    }

    @GetMapping("/phoneAlert")
    public PhoneAlertDto getPhoneByFirestation(@RequestParam("firestationNumber") String firestationNumber) {

        log.info("Chercher les numéros de téléphones des personnes desservie par la caserne numéro = {}", firestationNumber);
        return personService.getPhonesByFirestation(firestationNumber);
    }

    @GetMapping("/fire")
    public List<FireDto> getResidentsByAddress(@RequestParam("address") String address) {

        log.info("Chercher des personnes et des casernes la desservant via leurs address  = {}", address);
        return personService.getResidentsByAddress(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<FloodDto>> getResidentsWithByStationNumber(@RequestParam("stations") List<String> stations) {

        log.info("Chercher tous les foyers desservie par la casernes via = {}", stations);
        return personService.getResidentsByStationNumber(stations);
    }

    @GetMapping("/childAlert")
    public ChildDto getChildrenByAddress(@RequestParam("address") String address) {

        log.info("Chercher un liste d'enfants agé de 18 ans ou moins par l'address = {}", address);
        return personService.getChildrenByAddress(address);

    }

}


