package com.safetyNet.alert.utils;

import com.safetyNet.alert.utils.CalculateAge;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateAgetest {
CalculateAge calculateAge;

    @Test
    void calculateAgeTest() {
        // Given
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        CalculateAge calculateAge = new CalculateAge();

        // When
        int age = calculateAge.calculateAge(birthDate);

        // Then
        assertEquals(33, age);
    }


        @Test
        public void testGetDate() {
            // Given
            String dateStr = "15/04/2022";
            LocalDate expectedDate = LocalDate.of(2022, 4, 15);

            // When
            LocalDate actualDate = getDate(dateStr);

            // Then
            assertEquals(expectedDate, actualDate, "Date should match");
        }

        public LocalDate getDate(String date){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, formatter);
        }
    }



