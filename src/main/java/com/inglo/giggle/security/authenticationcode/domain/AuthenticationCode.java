package com.inglo.giggle.security.authenticationcode.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationCode {

    private String email;

    private String value;

    @Builder
    public AuthenticationCode(String email, String value) {
        this.email = email;
        this.value = value;
    }
}