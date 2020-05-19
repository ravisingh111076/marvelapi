package com.ravi.marvel.integration;

import com.ravi.marvel.domain.GetTranslationResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//RapidAPI client(supports google and microsoft translator)
@FeignClient(name = "languageClient" ,url = "${translator.url}")
public interface TranslatorFeignService {
    @RequestMapping(path={"/translate"},method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_XML_VALUE)
    GetTranslationResponse translate(@RequestHeader(value = "x-rapidapi-key") String apiKey,
                                       @RequestHeader(value = "x-rapidapi-host") String host,
                                       @RequestParam(value="from") String from,
                                       @RequestParam(value="to") String to,
                                       @RequestParam(value="text") String text);
}
