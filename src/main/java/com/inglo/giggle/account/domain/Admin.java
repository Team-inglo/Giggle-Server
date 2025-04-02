package com.inglo.giggle.account.domain;

import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Admin extends Account {

    @Builder
    public Admin(UUID id, ESecurityProvider provider, String serialId, String password,
                 String email
    ) {
        super(
                id,
                provider,
                serialId,
                password,
                email,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.ADMIN;
    }

    @Override
    public String getName() {
        return "관리자";
    }
}

