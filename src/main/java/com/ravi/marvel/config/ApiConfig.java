package com.ravi.marvel.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.text.SimpleDateFormat;


@Configuration
public class ApiConfig {
    @Bean
    public ObjectMapper objectMapper(JsonComponentModule jsonComponentModule) {
        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(jsonComponentModule)
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .featuresToDisable(
                        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .indentOutput(false)
                .build();
    }
}
