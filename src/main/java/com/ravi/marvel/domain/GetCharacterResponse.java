package com.ravi.marvel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCharacterResponse {

    @JsonProperty("data")
    private MarvelResponse marvelResponse;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MarvelResponse {
        @JsonProperty("offset")
        private Integer offset;
        @JsonProperty("limit")
        private Integer limit;
        @JsonProperty("results")
        List<Character> characterList;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Character {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("thumbnail")
        Thumbnail thumbnail;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Thumbnail {
        String path;
        String extension;
    }
}
