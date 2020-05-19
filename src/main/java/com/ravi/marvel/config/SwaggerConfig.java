package com.ravi.marvel.config;

import com.ravi.marvel.api.ApiController;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {ApiController.class})
public class SwaggerConfig {

    @Autowired
    private Environment environment;

    @Value("${swagger.host}")
    private String host;

    @Bean
    public Docket checkoutApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .groupName("CharacterAPI")
                .apiInfo(publicInfo())
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(XMLGregorianCalendar.class, Date.class)
                .useDefaultResponseMessages(false)
                .select()
                .paths(paths())
                .build();
    }

    private Predicate<String> paths() {
        return or(regex("/.*"), regex("/.*"));
    }

    private ApiInfo publicInfo() {
        return new ApiInfoBuilder()
                .title("Character API")
                .description("API for Marvel Charater")
                .version(environment.getProperty("info.app.version"))
                .build();
    }
}
