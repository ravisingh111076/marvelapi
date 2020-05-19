package com.ravi.marvel.security;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Builder
@AllArgsConstructor
@Component
public class SecurityKeyProvider {
    private MarvelKeyProvider marvelKeyProvider;
    private TranslatorKeyProvider translatorKeyProvider;
}
