package com.ravi.marvel.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("translator")
public class TranslatorKeyProvider {
    @Setter
    @Getter
    private String apiKey;
    @Setter
    @Getter
    private String host;
}