package com.safetyNet.alert.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public class CalculateAge {

    public int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public LocalDate getDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
}


