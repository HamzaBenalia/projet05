package com.safetyNet.alert.controller;

import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.service.AllergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allergy")
@Slf4j
public class AllergyController {

    @Autowired
    AllergyService allergyService;

    @PostMapping
    public void addAllergy(@RequestBody Allergy allergy) {
        log.info("Creation d'allergies {}", allergy);
        allergyService.add(allergy);
    }

    @GetMapping
    public List<Allergy> getAll() {
        log.info("Chercher la liste d'allergies");
        return allergyService.getAll();
    }
}
