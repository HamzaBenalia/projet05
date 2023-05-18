package com.safetyNet.alert.service;

import com.safetyNet.alert.dto.CommunityEmail;
import com.safetyNet.alert.repository.CommunityEmailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {


    @InjectMocks
    CommunityEmailService communityEmailService;

    @Mock
    CommunityEmailRepository communityEmailRepository;

    @Captor
    private ArgumentCaptor<CommunityEmail> communityEmailArgumentCaptor;


    @Test
    public void addAllCommunityEmailTest() {

        CommunityEmail communityEmail = new CommunityEmail("Hamza", "Ben", "Hamza@gmail.com", "Toulouse");

        communityEmailService.addAll(communityEmail);

        Mockito.verify(communityEmailRepository, times(1)).saveEmailCommunity(communityEmailArgumentCaptor.capture());

        assertThat(communityEmailArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "city", "email")
                .containsExactly(communityEmail.getFirstName(), communityEmail.getLastName(), communityEmail.getCity(), communityEmail.getEmail());

    }

    @Test
    public void testGetAllCommunity() {

        // Create some persons
        CommunityEmail communityEmailDoe = new CommunityEmail("John", "Doe", "john.doe@example.com", "Toulouse");
        CommunityEmail communityEmailJane = new CommunityEmail("Jane", "Doe", "jane.doe@example.com", "Montpelllier");
        List<CommunityEmail> allCommunityEmail = Arrays.asList(communityEmailDoe, communityEmailJane);

        when(communityEmailRepository.getAllCommunity()).thenReturn(allCommunityEmail);

        List<CommunityEmail> result = communityEmailService.getCommunity();
        Mockito.verify(communityEmailRepository, times(1)).getAllCommunity();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(communityEmailDoe));
        Assertions.assertTrue(result.contains(communityEmailJane));
    }

    @Test
    public void testGetEmails() {

        CommunityEmail communityEmailHamza = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");
        CommunityEmail communityEmailSara = new CommunityEmail("Sara", "Ben", "Sara@gmail.com", "Toulouse");

        List<CommunityEmail> communityEmails = new ArrayList<>();
        communityEmails.add(communityEmailHamza);
        communityEmails.add(communityEmailSara);
        //List<String> strings = new ArrayList<>();
        List<String> allEmails = new ArrayList<>();

        when(communityEmailService.getAllEmails()).thenReturn(allEmails);

        List<String> emails = communityEmailRepository.getEmails();

        String result = "Hamza@gmail.com";

        Assertions.assertNotNull(emails);


    }

    @Test
    public void testGetAllCities() {
        CommunityEmail communityEmailHamza = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");
        communityEmailRepository.saveEmailCommunity(communityEmailHamza);


        List<String> results = communityEmailService.getAllCitys();

        Assertions.assertNotNull(results);
        verify(communityEmailRepository, times(1)).getCitys();
    }

    /*@Test
    public void testGetEmailByCity() {
        CommunityEmail communityEmailHamza = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");
        communityEmailRepository.saveEmailCommunity(communityEmailHamza);

        List<String> emailsByCity = communityEmailService.getEmailsByCity(communityEmailHamza.getCity());
        when(communityEmailService.getEmailsByCity("Toulouse")).thenReturn(emailsByCity);

        Assertions.assertNotNull(emailsByCity);
        Assertions.assertTrue(emailsByCity.contains(communityEmailHamza.getEmail()));
    }
*/

}