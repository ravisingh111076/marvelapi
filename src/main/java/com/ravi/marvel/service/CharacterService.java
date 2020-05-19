package com.ravi.marvel.service;

import com.ravi.marvel.domain.GetCharacterResponse;
import com.ravi.marvel.api.MarvelCharacterResponse;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface CharacterService {
   MarvelCharacterResponse lookUpCharacter(String id, String language);

    /**
     * functions this service implementor should perform
     * so tasks are - make marvel service call, transform it into response,
     * translate description and set in response.
     */
   interface MakeMarvelServiceCall extends Function<String, Either<GetCharacterResponse>>{};
   interface TranslateCharacterDescription extends BiFunction<String, MarvelCharacterResponse, MarvelCharacterResponse> {};
   interface TransformFeignClientMarvelResponseToApiMarvelResponse extends Function<Either<GetCharacterResponse>, MarvelCharacterResponse>{};
}
