package com.ravi.marvel.service;

import com.ravi.marvel.integration.MarvelFeignClient;
import com.ravi.marvel.integration.TranslatorFeignService;
import com.ravi.marvel.domain.GetCharacterResponse;
import com.ravi.marvel.api.MarvelCharacterResponse;
import com.ravi.marvel.domain.GetTranslationResponse;
import com.ravi.marvel.security.SecurityKeyProvide;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final MarvelFeignClient marvelFeignClient;
    private final TranslatorFeignService translatorFeignService;
    private final SecurityKeyProvide securityKeyProvide;

    public CharacterServiceImpl(MarvelFeignClient marvelFeignClient,
                                TranslatorFeignService translatorFeignService,
                                SecurityKeyProvide securityKeyProvide) {
        this.marvelFeignClient = marvelFeignClient;
        this.translatorFeignService = translatorFeignService;
        this.securityKeyProvide = securityKeyProvide;
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
            try {
                //make call to feign client, check for error set error if
                Long timeStamp = System.currentTimeMillis();
                GetCharacterResponse response = marvelFeignClient.lookUpCharacterById(characterId,
                        securityKeyProvide.getMarvelKeyProvider().getPublic_key(),
                        timeStamp,
                        securityKeyProvide.getMarvelKeyProvider().getKey(timeStamp));
            return Either.toRight(response);
            }catch (Exception exception) {
                return Either.toLeft(new RuntimeException("some thing went wrong"));
            }
        };
    }

    TranslateCharacterDescription translateCharacterDescription() {
        return (language, response) -> {
            if(null == language ||
                    Locale.ENGLISH.toString().equalsIgnoreCase(language)) {
                    return response;
            }
            GetTranslationResponse getTranslationResponse = translatorFeignService.translate(
                    securityKeyProvide.getTranslatorKeyProvider().getApiKey(),
                    securityKeyProvide.getTranslatorKeyProvider().getHost(),
                    Locale.ENGLISH.toString(),language, response.getDescription());
            response.setDescription(getTranslationResponse.getString());
            return response;
        };
    }

    TransformFeignClientMarvelResponseToApiMarvelResponse transformMarvelServiceResponseToApiResponse() {
        return (marvelCharacter -> {
            if(marvelCharacter.hasException()) {
                throw marvelCharacter.exception();
            }
            GetCharacterResponse.Character character = marvelCharacter.rightValue()
                    .getMarvelResponse().getCharacterList().get(0);
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