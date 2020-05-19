package com.ravi.marvel.api;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@ApiModel(value="MarvelCharacter", description = "model from marvel api")
public class MarvelCharacterResponse {
    Long id;
    String name;
    String description;
    Thumbnail thumbnail;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Thumbnail {
        String path;
        String extension;
    }
}
