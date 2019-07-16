package com.example.application.validator;

import com.example.application.model.Artist;
import com.example.application.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;

@Component
public class ArtistValidator implements Validator {

    @Autowired
    private ArtistRepository artistRepository;



    @Override
    public boolean supports(Class<?> aClass) {
        return Artist.class.equals(aClass);

    }

    @Override
    public void validate(Object o, Errors errors) {
        Artist artist=(Artist)o;
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        if(artist.getStartActivityYear()<1900 || artist.getEndActivityYear()>year){
            errors.rejectValue("yearValidate", "Incorrect year interval");
        }

    }
}
