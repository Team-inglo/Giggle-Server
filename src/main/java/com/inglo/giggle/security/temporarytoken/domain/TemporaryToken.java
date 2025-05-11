package com.inglo.giggle.security.temporarytoken.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TemporaryToken {

    private String email;
    private String value;

    @Builder
    public TemporaryToken(String email, String value) {
        this.email = email;
        this.value = value;
    }
}
