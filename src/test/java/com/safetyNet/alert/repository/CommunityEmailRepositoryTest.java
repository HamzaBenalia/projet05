package com.safetyNet.alert.repository;

import com.safetyNet.alert.dto.CommunityEmail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommunityEmailRepositoryTest {

    private final CommunityEmailRepository communityEmailRepository = new CommunityEmailRepository();
    private final CommunityEmail communityEmail = new CommunityEmail();

    @Test
    public void testSaveEmailCommunity() {
        // Créer une communauté email pour le test
        CommunityEmail communityEmail = new CommunityEmail();

        // Ajouter la communauté email
        communityEmailRepository.saveEmailCommunity(communityEmail);

        // Vérifier que la communauté email a été ajoutée
        assertTrue(communityEmailRepository.getAllCommunity().contains(communityEmail));
    }

    @Test
    public void testGetAllCommunity() {
        // Créer une liste de communautés email pour le test
        List<CommunityEmail> expectedCommunityEmails = new ArrayList<>();
        expectedCommunityEmails.add(new CommunityEmail());
        expectedCommunityEmails.add(new CommunityEmail());
        expectedCommunityEmails.add(new CommunityEmail());

        // Ajouter les communautés email à la liste interne de la classe

        communityEmailRepository.getAllCommunity().addAll(expectedCommunityEmails);

        // Récupérer toutes les communautés email avec la méthode getAllCommunity()
        List<CommunityEmail> actualCommunityEmails = communityEmailRepository.getAllCommunity();

        // Vérifier que le nombre de communautés email est le même
        assertEquals(expectedCommunityEmails.size(), actualCommunityEmails.size());

        // Vérifier que chaque communauté email attendue est bien présente dans la liste obtenue
        for (CommunityEmail expectedCommunityEmail : expectedCommunityEmails) {
            assertTrue(actualCommunityEmails.contains(expectedCommunityEmail));
        }
    }

    @Test
    public void testGetEmails() {
        CommunityEmail communityEmail = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");

        communityEmailRepository.saveEmailCommunity(communityEmail);

        List<String> emails = communityEmailRepository.getEmails();
        String result = ("Hamza@gmail.com");

        Assertions.assertEquals(emails, List.of(result));

    }

    @Test
    public void testGetCity() {
        CommunityEmail communityEmail = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");

        communityEmailRepository.saveEmailCommunity(communityEmail);
        List<String> cities = communityEmailRepository.getCitys();

        String result = ("Toulouse");
        Assertions.assertEquals(cities, List.of(result));
    }

    @Test
    public void testFindEmailByCity() {
        CommunityEmail communityEmail = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");
        communityEmailRepository.saveEmailCommunity(communityEmail);

        String city = communityEmail.getCity();

        List<String> emailbyCity = communityEmailRepository.findEmailByCity(city);

        Assertions.assertEquals(Collections.singletonList(communityEmail.getEmail()), emailbyCity);


    }

}

