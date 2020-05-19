package com.ravi.marvel.api;

import com.ravi.marvel.cache.MarvelCharacterCacheComponent;
import com.ravi.marvel.service.CharacterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation(value = "get all marvel character ids")
    @ApiResponses({@ApiResponse(code = 200, message = "Get all marvel characters id"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 415, message = "Unsupported Media Type"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping(name = "get-characters", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCharacterIds() {
        List<Long> marvelCharacterIds = marvelCharacterCacheComponent.lookUpAllCharacterIds();
        return new ResponseEntity<>(marvelCharacterIds, HttpStatus.OK);
    }


    @ApiOperation(value = "get marvel character by id", response = MarvelCharacterResponse.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Get marvel characters by id"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 415, message = "Unsupported Media Type"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping(name = "lookUpCharacterById", value = {"/{characterId}"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> lookUpCharacter(@ApiParam(name = "characterId", type = "String", required = true)
                                                 @PathVariable(value = "characterId") String id,
                                             @ApiParam(name = "language", type = "String", example = "fr")
                                             @RequestParam(value = "language", required = false) String lang) {
        //other approach could be use WebMvcConfigurerAdapter,may be extend ApiConfig class,
        // add a interceptor and preHandle all errors. make code more cleaner,but in this case.
        // we have only 1 potential error check.
        Optional<String> validationError = validator.validateLanguage(lang);
        if (validationError.isPresent()) {
            return new ResponseEntity<>(validationError.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(characterService.lookUpCharacter(id, lang), HttpStatus.OK);
    }
}
