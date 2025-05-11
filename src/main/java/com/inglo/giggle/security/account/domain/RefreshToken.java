package com.inglo.giggle.security.account.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RefreshToken {
    private UUID accountId;
    private String value;

    @Builder
    public RefreshToken(UUID accountId, String value) {
        this.accountId = accountId;
        this.value = value;
    }
}
