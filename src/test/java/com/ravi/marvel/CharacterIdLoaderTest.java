package com.ravi.marvel;

import com.ravi.marvel.integration.MarvelFeignClient;
import com.ravi.marvel.domain.GetCharacterResponse;
import com.ravi.marvel.integration.loader.CharacterIdLoader;
import com.ravi.marvel.security.MarvelKeyProvider;
import com.ravi.marvel.security.SecurityKeyProvider;
import com.ravi.marvel.security.TranslatorKeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterIdLoaderTest {
    @Mock
    private MarvelFeignClient marvelFeignClient;
    @Mock
    private MarvelKeyProvider marvelKeyProvider;
    @Mock
    private TranslatorKeyProvider translatorKeyProvider;

    @InjectMocks
    private SecurityKeyProvider securityKeyProvider;

    @InjectMocks
    private CharacterIdLoader characterIdLoader;

    @Test
    public void testLoadCharacterIds_Limit_100_Total_1493() throws NoSuchAlgorithmException{
        characterIdLoader.setTotal(1493);
        characterIdLoader.setLimit(100);
        when(marvelKeyProvider.getKey(anyLong())).thenReturn("test-api-key");
        when(marvelKeyProvider.getPublic_key()).thenReturn("public-key");
        IntStream.range(1, 16)
                .forEach(val -> {
                    when(marvelFeignClient.getMarvelCharacters(eq("public-key")
                        ,eq("test-api-key"),
                        anyLong(), eq(100), eq((val-1)*100))).thenReturn(GetCharacterResponse
                        .builder().marvelResponse(GetCharacterResponse.MarvelResponse
                                .builder().characterList(Arrays.asList(GetCharacterResponse.Character.builder().
                                        id(10000l + val).build())).build()).build());});
        List<Long> listOfId = characterIdLoader.loadCharacterIds();
        assertEquals(15, listOfId.size());
    }

    public void testXXX() {
//        long timestamp = System.currentTimeMillis();
//        System.out.println(timestamp);
//        System.out.println(DigestUtils.md5Hex(timestamp+"041997a8cc0647cf24f589e066f65aa7e8746bb2"+"4f72eb2f1aff51a652ea95d417b69edc"));

    }
}
