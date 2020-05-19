package com.ravi.marvel.cache;

import com.ravi.marvel.integration.loader.CharacterIdLoader;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MarvelCharacterCacheComponentImpl implements MarvelCharacterCacheComponent{

    private final CharacterIdLoader characterIdLoader;

    public MarvelCharacterCacheComponentImpl(CharacterIdLoader characterIdLoader) {
        this.characterIdLoader = characterIdLoader;
    }

    @Override
    @Cacheable(value = "characterCache", key="'marvelCharacterId'")
    public List<Long> lookUpAllCharacterIds() {
        return characterIdLoader.loadCharacterIds();
    }
}
