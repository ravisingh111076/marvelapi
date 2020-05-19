package com.ravi.marvel.security;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.security.NoSuchAlgorithmException;


@Component
@ConfigurationProperties("marvel")
public class MarvelKeyProvider {
    @Setter
    @Getter
    private String public_key;
    @Setter
    private String private_key;

    public String getKey(Long timestamp) throws NoSuchAlgorithmException {
        return DigestUtils.md5Hex(timestamp + private_key+public_key);
    }
}
