package com.ravi.marvel.api;

import com.ravi.marvel.cache.MarvelCharacterCacheComponent;
import com.ravi.marvel.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/characters")
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final MarvelCharacterCacheComponent marvelCharacterCacheComponent;
    private final CharacterService characterService;
    private final RequestValidator validator;

    @GetMapping(name="get-characters", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCharacterIds() {
        List<Long> marvelCharacterIds = marvelCharacterCacheComponent.lookUpAllCharacterIds();
        return new ResponseEntity<>(marvelCharacterIds, HttpStatus.OK);
    }

    @GetMapping(name="lookUpCharacterById",value= {"/{characterId}"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> lookUpCharacter(@PathVariable(value = "characterId") String id,
                                             @RequestParam(value="language", required = false) String lang) {
        //other approach could be use WebMvcConfigurerAdapter,may be extend ApiConfig class,
        // add a interceptor and preHandle all errors. make code more cleaner,but in this case.
        // we have only 1 potential error check.
        Optional<String> validationError = validator.validateLanguage(lang);
        if(validationError.isPresent()) {
            return new ResponseEntity<>(validationError.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(characterService.lookUpCharacter(id,lang), HttpStatus.OK);
    }
}
