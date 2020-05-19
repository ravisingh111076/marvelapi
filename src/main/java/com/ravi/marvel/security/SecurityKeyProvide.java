package com.ravi.marvel.security;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Builder
@AllArgsConstructor
@Component
public class SecurityKeyProvide {
    private MarvelKeyProvider marvelKeyProvider;
    private TranslatorKeyProvider translatorKeyProvider;
}
