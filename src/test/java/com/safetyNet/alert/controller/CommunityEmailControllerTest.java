package com.safetyNet.alert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.alert.dto.CommunityEmail;
import com.safetyNet.alert.service.CommunityEmailService;
import com.safetyNet.alert.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
public class CommunityEmailControllerTest {

    @MockBean
    CommunityEmailService communityEmailService;
    @MockBean
    PersonService personService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void communityEmailContoller_createCommunityEmail_returnCreated() throws Exception {
        CommunityEmail communityEmail = new CommunityEmail();
        communityEmail.setFirstName("John");
        communityEmail.setLastName("Doe");
        communityEmail.setCity("Toulouse");
        communityEmail.setEmail("Hamza@gmail.com");

        String json = objectMapper.writeValueAsString(communityEmail);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/communityEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        verify(communityEmailService, times(1)).addAll(any(CommunityEmail.class));
    }

   /* @Test
    public void communityEmailController_getAllCommunityEmailTest_returnAllCommmunityEmail() throws Exception {

        CommunityEmail communityEmail = new CommunityEmail("Hamza", "Ben", "Hamza@gmail.com", "Toulouse");

        List<CommunityEmail> communityEmailList = new ArrayList<>();

        communityEmailList.add(communityEmail);

        List<String> getemails = communityEmailService.getAllEmails();


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Hamza"))
                .andExpect(jsonPath("$[0].address").value("Toulouse"))
                .andReturn();

        verify(communityEmailService, times(1)).getAllEmails();

    }*/
}