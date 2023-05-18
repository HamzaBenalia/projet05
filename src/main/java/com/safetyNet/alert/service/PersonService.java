package com.safetyNet.alert.service;

import com.safetyNet.alert.dto.childAlert.ChildDataDto;
import com.safetyNet.alert.dto.childAlert.ChildDto;
import com.safetyNet.alert.dto.fire.FireDataDto;
import com.safetyNet.alert.dto.fire.FireDto;
import com.safetyNet.alert.dto.flood.FloodDataDto;
import com.safetyNet.alert.dto.flood.FloodDto;
import com.safetyNet.alert.dto.personInfo.PersonDataDto;
import com.safetyNet.alert.dto.personInfo.PersonInfoDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDataDto;
import com.safetyNet.alert.dto.phoneAlert.PhoneAlertDto;
import com.safetyNet.alert.dto.stationDto.PersonStationDto;
import com.safetyNet.alert.dto.stationDto.StationInfoDto;
import com.safetyNet.alert.model.Allergy;
import com.safetyNet.alert.model.Firestation;
import com.safetyNet.alert.model.Medicalrecord;
import com.safetyNet.alert.model.Person;
import com.safetyNet.alert.repository.AllergyRepository;
import com.safetyNet.alert.repository.FirestationRepository;
import com.safetyNet.alert.repository.MedicalrecordRepository;
import com.safetyNet.alert.repository.PersonRepository;
import com.safetyNet.alert.utils.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalrecordRepository medicalrecordRepository;
    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private FirestationRepository firestationRepository;


    public void add(Person person) {
        if (getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName()) != null) {
            return;
        }
        personRepository.save(person);
    }


    public List<Person> getAll() {
        return personRepository.getAll();
    }

    public Person getPersonByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = personRepository.getAll();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    public List<Person> deleteByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = personRepository.getAll();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                persons.remove(person);
                return persons;
            }
        }
        return null;
    }

    public void updatePerson(Person updatePerson) {
        personRepository.updatePerson(updatePerson);
    }


    public List<PersonInfoDto> getPersonInfos(String firstName, String lastName) {
        CalculateAge calculateAge = new CalculateAge();
        List<PersonInfoDto> personInfoDtos = new ArrayList<>();
        List<Person> personList = personRepository.getPersonByName(lastName);
        for (Person person : personList) {
            List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            PersonDataDto personDataDto = new PersonDataDto();
            personDataDto.setAddress(person.getAddress());
            LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
            int age = calculateAge.calculateAge(birthDate);
            personDataDto.setAge(age);
            personDataDto.setEmail(person.getEmail());
            personDataDto.setFirstName(person.getFirstName());
            personDataDto.setLastName(person.getLastName());
            PersonInfoDto personInfoDto = new PersonInfoDto(personDataDto, allergieList, medicalrecordList);
            personInfoDtos.add(personInfoDto);
        }
        return personInfoDtos;
    }

    public List<Person> getPersonByphone(String phone) {
        return personRepository.getPersonByPhone(phone);
    }

    public StationInfoDto getPersonByFirestation(String firestationNumber) {
        CalculateAge calculateAge = new CalculateAge();
        StationInfoDto stationInfoDto = new StationInfoDto();
        int minors = 0;
        int adults = 0;
        List<PersonStationDto> personStationDtos = new ArrayList<>();
        List<Person> personList = personRepository.getAll();
        List<String> addresses = firestationRepository.getAdresseByStation(firestationNumber);
        for (Person person : personList) {
            if (addresses.contains(person.getAddress())) {
                PersonStationDto personStationDto = new PersonStationDto();
                personStationDto.setAddress(person.getAddress());
                personStationDto.setFirstName(person.getFirstName());
                personStationDto.setLastName(person.getLastName());
                personStationDto.setPhone(person.getPhone());
                personStationDtos.add(personStationDto);
                LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
                int age = calculateAge.calculateAge(birthDate);
                if (age < 18) {
                    minors++;
                } else {
                    adults++;
                }

            }
        }
        stationInfoDto.setPersonStationDtos(personStationDtos);
        stationInfoDto.setMinor(minors);
        stationInfoDto.setAdult(adults);
        return stationInfoDto;
    }

    public PhoneAlertDto getPhonesByFirestation(String firestationNumber) {
        PhoneAlertDto phoneAlertDto = new PhoneAlertDto();
        List<PhoneAlertDataDto> phoneAlertDataDtos = new ArrayList<>();
        List<Person> personList = personRepository.getAll();
        List<String> addresses = firestationRepository.getAdresseByStation(firestationNumber);
        for (Person person : personList) {
            if (addresses.contains(person.getAddress())) {
                PhoneAlertDataDto phoneAlertDataDto = new PhoneAlertDataDto();
                phoneAlertDataDto.setPhone(person.getPhone());
                phoneAlertDataDtos.add(phoneAlertDataDto);
            }
        }
        phoneAlertDto.setPhoneAlertDataDtos(phoneAlertDataDtos);

        return phoneAlertDto;
    }


    public List<FireDto> getResidentsByAddress(String address) {
        CalculateAge calculateAge = new CalculateAge();
        List<FireDto> fireDataDtos = new ArrayList<>();
        for (Firestation firestation : firestationRepository.getAll()) {
            for (Person person : personRepository.getPersonByAddress(address)) {
                if (firestation.getAddress().equals(address)) {
                    List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    FireDataDto fireDataDto = new FireDataDto();
                    LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
                    int age = calculateAge.calculateAge(birthDate);
                    fireDataDto.setAge(String.valueOf(age));
                    fireDataDto.setPhone(person.getPhone());
                    fireDataDto.setFirstName(person.getFirstName());
                    fireDataDto.setStationNumber(firestation.getStation());
                    FireDto fireDto = new FireDto(fireDataDto, allergieList, medicalrecordList);
                    fireDataDtos.add(fireDto);
                }
            }

        }

        return fireDataDtos;
    }

    public Map<String, List<FloodDto>> getResidentsByStationNumber(List<String> stations) {
        CalculateAge calculateAge = new CalculateAge();
        Map<String, List<FloodDto>> map = new HashMap<>();
        for (String station : stations) {

            for (Firestation firestation1 : firestationRepository.getFirestationByStation(station)) {
                List<FloodDto> floodDataDtos = new ArrayList<>();
                for (Person person : personRepository.getAll()) {
                    if (firestation1.getAddress().equals(person.getAddress())) {
                        Optional<FloodDto> optionalFloodDto = floodDataDtos.stream().filter(floodDto -> floodDto.getFloodDataDto().getFirstName().equals(person.getFirstName())
                                && floodDto.getFloodDataDto().getLastName().equals(person.getLastName())).findFirst();
                        if (optionalFloodDto.isEmpty()) {
                            List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                            List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                            FloodDataDto floodDataDto = new FloodDataDto();
                            LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
                            int age = calculateAge.calculateAge(birthDate);
                            floodDataDto.setAge(String.valueOf(age));
                            floodDataDto.setPhone(person.getPhone());
                            floodDataDto.setFirstName(person.getFirstName());
                            FloodDto floodDto1 = new FloodDto(floodDataDto, allergieList, medicalrecordList);
                            floodDataDtos.add(floodDto1);
                        }
                    }
                }
                map.put(firestation1.getAddress(), floodDataDtos);
            }
        }
        return map;
    }


    public ChildDto getChildrenByAddress(String address) {
        CalculateAge calculateAge = new CalculateAge();
        ChildDto childDto = new ChildDto();
        List<ChildDataDto> childDataDtos = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        List<Person> persons = personRepository.getPersonByAddress(address);
        Optional<Person> optionalPerson = persons.stream().filter(person -> calculateAge.calculateAge(calculateAge.getDate(person.getBirthdate())) < 18).findFirst();
        if (optionalPerson.isPresent()) {
            for (Person person : persons) {
                int age = calculateAge.calculateAge(calculateAge.getDate(person.getBirthdate()));
                if (age < 18) {
                    ChildDataDto childDataDto = new ChildDataDto();
                    childDataDto.setAge(age);
                    childDataDto.setFirstName(person.getFirstName());
                    childDataDto.setLastName(person.getLastName());
                    childDataDtos.add(childDataDto);
                } else {
                    adults.add(person);
                }
            }
        }
        childDto.setChildren(childDataDtos);
        childDto.setAdults(adults);
        return childDto;
    }
}


