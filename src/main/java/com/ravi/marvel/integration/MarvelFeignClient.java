package com.ravi.marvel.integration;

import com.ravi.marvel.domain.GetCharacterResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "marvelClient" ,url = "${marvel.url}")
public interface MarvelFeignClient {

    @RequestMapping(path = "/v1/public/characters",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GetCharacterResponse getMarvelCharacters(@RequestParam(value = "apikey") String apiKey,
                                                       @RequestParam(value = "hash") String hash,
                                                       @RequestParam(value = "ts") Long timestamp,
                                                       @RequestParam(value = "limit") Integer limit,
                                                       @RequestParam(value = "offset") Integer offset);

    @RequestMapping(path="/v1/public/characters/{characterId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GetCharacterResponse lookUpCharacterById(@PathVariable(value="characterId") String characterId,
                                                @RequestParam(value = "apikey") String apiKey,
                                                @RequestParam(value = "ts") Long timestamp,
                                                @RequestParam(value = "hash") String hash);

}
