package com.safetyNet.alert.repository;

import com.safetyNet.alert.dto.CommunityEmail;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommunityEmailRepository {

    private List<CommunityEmail> communityEmails = new ArrayList<>();


    public void saveEmailCommunity(CommunityEmail communityEmail) {
        communityEmails.add(communityEmail);

    }

    public List<CommunityEmail> getAllCommunity() {
        return communityEmails;
    }


    public List<String> getEmails() {
        List<String> emailList = new ArrayList<>();
        for (CommunityEmail email : communityEmails) {
            if (email.getEmail() != null)
                emailList.add(email.getEmail());
        }
        return emailList;
    }

    public List<String> getCitys() {
        List<String> emailList = new ArrayList<>();
        for (CommunityEmail email : communityEmails) {
            if (email.getCity() != null)
                emailList.add(email.getCity());
        }
        return emailList;
    }

    public List<String> findEmailByCity(String city) {
        List<String> emailList = new ArrayList<>();
        for (CommunityEmail email : communityEmails) {
            if (email.getCity().equals(city))
                emailList.add(email.getEmail());
        }
        return emailList;
    }

}


