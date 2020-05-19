package com.ravi.marvel.service;

import com.ravi.marvel.api.MarvelCharacterResponse;
import com.ravi.marvel.integration.MarvelFeignClient;
import com.ravi.marvel.integration.TranslatorFeignService;
import com.ravi.marvel.domain.GetCharacterResponse;
import com.ravi.marvel.domain.GetTranslationResponse;
import com.ravi.marvel.security.MarvelKeyProvider;
import com.ravi.marvel.security.SecurityKeyProvider;
import com.ravi.marvel.security.TranslatorKeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceImplTest {

    @Mock
    MarvelFeignClient marvelFeignClient;
    @Mock
    TranslatorFeignService translatorFeignService;
    @Mock
    private MarvelKeyProvider marvelKeyProvider;
    @Mock
    private TranslatorKeyProvider translatorKeyProvider;

    @InjectMocks
    private SecurityKeyProvider securityKeyProvider;

    @InjectMocks
    private CharacterServiceImpl marvelCharacterService;

    @Test
    public void testLookUpCharacter() throws Exception {

        when(marvelKeyProvider.getKey(anyLong())).thenReturn("key");
        when(marvelKeyProvider.getPublic_key()).thenReturn("publicKey");
        when(translatorKeyProvider.getApiKey()).thenReturn("apikey");
        when(translatorKeyProvider.getHost()).thenReturn("host");

        marvelCharacterService = new CharacterServiceImpl(marvelFeignClient,
                translatorFeignService, securityKeyProvider);

        GetCharacterResponse getCharacterResponse = GetCharacterResponse.builder()
                .marvelResponse(GetCharacterResponse.MarvelResponse.builder()
                        .characterList(Arrays.asList(GetCharacterResponse.Character
                                .builder().id(100l)
                                .description("testdescription")
                                .name("testname")
                                .thumbnail(GetCharacterResponse.Thumbnail.builder()
                                        .path("path").extension("ext").
                                                build()).build())).build()).build();
        GetTranslationResponse getTranslationResponse = GetTranslationResponse.builder()
                .string("testdescription-In-Frence").build();

        when(marvelFeignClient.lookUpCharacterById(eq("testid"),
                eq("publicKey"), anyLong(), eq("key"))).thenReturn(getCharacterResponse);
        when(translatorFeignService.translate(eq("apikey"),eq("host"),
                eq(Locale.ENGLISH.toString()),
                        eq("fr"),
                        eq("testdescription"))).thenReturn(getTranslationResponse);
        MarvelCharacterResponse response = marvelCharacterService.lookUpCharacter("testid", "fr");

        assertEquals("testdescription-In-Frence",response.getDescription());
        assertEquals(100l, response.getId().longValue());
        assertEquals("testname", response.getName());
    }
}
