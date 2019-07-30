package com.example.application.utils;

import com.example.application.exceptions.CustomException;

import java.time.Year;

public class ValidateCurrentYear {

    public static int ValidateYear(int year) {
        if ((year < 1900) || (year > Year.now().getValue())) {
            throw new CustomException("Year should be from 1900 to current year");
        }
        return year;
    }
}
