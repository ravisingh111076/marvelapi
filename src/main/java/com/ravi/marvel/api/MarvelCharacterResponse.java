package com.ravi.marvel.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
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
