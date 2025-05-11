package com.inglo.giggle.security.temporaryaccount.domain;

import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TemporaryAccount {

    private String email;

    private String password;

    private ESecurityRole accountType;

    @Builder
    public TemporaryAccount(String email, String password, ESecurityRole accountType) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
}
