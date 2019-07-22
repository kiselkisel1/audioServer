package com.example.application.utils;

import com.example.application.exceptions.CustomException;

import java.util.Calendar;

public class CurrentYear {

  public static void validateYear(int year){

     if(year > Calendar.getInstance().get(Calendar.YEAR) || year<1900){
       throw new CustomException("Year should be more from 1900 to current year");
    }
  }
}
