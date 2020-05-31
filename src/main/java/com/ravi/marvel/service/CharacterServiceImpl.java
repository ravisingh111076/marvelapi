package com.ravi.marvel.service;

import com.ravi.marvel.integration.MarvelFeignClient;
import com.ravi.marvel.integration.TranslatorFeignService;
import com.ravi.marvel.domain.GetCharacterResponse;
import com.ravi.marvel.api.MarvelCharacterResponse;
import com.ravi.marvel.domain.GetTranslationResponse;
import com.ravi.marvel.security.SecurityKeyProvider;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final MarvelFeignClient marvelFeignClient;
    private final TranslatorFeignService translatorFeignService;
    private final SecurityKeyProvider securityKeyProvider;

    public CharacterServiceImpl(MarvelFeignClient marvelFeignClient,
                                TranslatorFeignService translatorFeignService,
                                SecurityKeyProvider securityKeyProvider) {
        this.marvelFeignClient = marvelFeignClient;
        this.translatorFeignService = translatorFeignService;
        this.securityKeyProvider = securityKeyProvider;
    }

    @Override
    public MarvelCharacterResponse lookUpCharacter(String characterId, String language) {
        return translateCharacterDescription().apply(language,
                transformMarvelServiceResponseToApiResponse()
                        .apply(makeMarvelServiceCall()
                                .apply(characterId)));
    }

    MakeMarvelServiceCall makeMarvelServiceCall() {
        return characterId -> {
            Long timeStamp = System.currentTimeMillis();
            return Try.of(() -> marvelFeignClient.lookUpCharacterById(characterId,
                    securityKeyProvider.getMarvelKeyProvider().getPublic_key(),
                    timeStamp,
                    securityKeyProvider.getMarvelKeyProvider().getKey(timeStamp))).toEither();
        };
    }

    TranslateCharacterDescription translateCharacterDescription() {
        return (language, response) -> {
            if(null == language ||
                    Locale.ENGLISH.toString().equalsIgnoreCase(language)) {
                    return response;
            }
            GetTranslationResponse getTranslationResponse = Try.of(()-> translatorFeignService.translate(
                    securityKeyProvider.getTranslatorKeyProvider().getApiKey(),
                    securityKeyProvider.getTranslatorKeyProvider().getHost(),
                    Locale.ENGLISH.toString(), language, response.getDescription())).getOrElseThrow((ex) -> new RuntimeException(ex.getMessage()));
            response.setDescription(getTranslationResponse.getString());
            return response;
        };
    }

    TransformFeignClientMarvelResponseToApiMarvelResponse transformMarvelServiceResponseToApiResponse() {
        return (marvelCharacter -> {
            GetCharacterResponse.Character character = marvelCharacter.getOrElseThrow((exception)->
                    new RuntimeException(exception.getMessage())).getMarvelResponse().getCharacterList().get(0);
            return MarvelCharacterResponse.builder()
                    .description(character.getDescription())
                    .id(character.getId())
                    .name(character.getName())
                    .thumbnail(MarvelCharacterResponse.Thumbnail
                            .builder()
                            .path(character.getThumbnail().getPath())
                            .extension(character.getThumbnail().getExtension())
                            .build())
                    .build();
        });
    }
}
