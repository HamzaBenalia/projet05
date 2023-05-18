package com.safetyNet.alert.repository;

import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    @Autowired
    MedicalrecordRepository medicalrecordRepository;
    AllergyRepository allergyRepository;
    private final List<Person> persons = new ArrayList<>();
    private final List<Firestation> firestations = new ArrayList<>();
    private final List<Medicalrecord> medicalrecords = new ArrayList<>();

    public void save(Person person) {
        persons.add(person);
    }

    public List<Person> getAll() {
        return persons;
    }


    public void deletePerson(Person person) {
        persons.remove(person);
    }

    public void updatePerson(Person updatedPerson) {
        for (Person person : persons) {
            if (person.getFirstName().equals(updatedPerson.getFirstName()) && person.getLastName().equals(updatedPerson.getLastName())) {
                person.setAddress(updatedPerson.getAddress());
                person.setEmail(updatedPerson.getEmail());
                person.setCity(updatedPerson.getCity());
                person.setPhone(updatedPerson.getPhone());
                person.setZip(updatedPerson.getZip());
            }
        }

    }

    public List<Person> getPersonByName(String lastName) {
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            if (person.getLastName().equals(lastName)) {
                personList.add(person);
            }
        }
        return personList;
    }

    public List<Person> getPersonByAddress(String address) {
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                personList.add(person);
            }
        }
        return personList;
    }

    public List<Person> getPersonByPhone(String phone) {
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            if (person.getPhone().equals(phone)) {
                personList.add(person);

            }
        }
        return personList;
    }

}
