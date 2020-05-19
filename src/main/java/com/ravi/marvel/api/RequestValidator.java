package com.ravi.marvel.api;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Component
public class RequestValidator {

    public Optional<String> validateLanguage(String language) {
        if(language == null) { return Optional.empty();}
           if(Arrays.asList(Locale.getISOLanguages()).contains(language)) {
               return Optional.empty();
           }else {
              return Optional.of("Language not supported");
           }
        }
}
