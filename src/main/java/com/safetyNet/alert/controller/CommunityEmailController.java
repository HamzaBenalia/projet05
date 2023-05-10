package com.safetyNet.alert.controller;

import com.safetyNet.alert.dto.CommunityEmail;
import com.safetyNet.alert.service.CommunityEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    private CommunityEmailService communityEmailService;

    // Endpoint pour obtenir les adresses email de tous les habitants

    @PostMapping
    public void saveCommunityEmail(@RequestBody CommunityEmail communityEmail) {
        communityEmailService.addAll(communityEmail);
    }


    @GetMapping("/allemails")
    public List<String> getAllEmails() {
        return communityEmailService.getAllEmails();
    }

    @GetMapping("/allcitys")
    public List<String> getAllCitys() {
        return communityEmailService.getAllCitys();
    }

    @GetMapping
    public List<String> getEmailsByCity(@RequestParam String city) {
        return communityEmailService.getEmailsByCity(city);
    }
}



