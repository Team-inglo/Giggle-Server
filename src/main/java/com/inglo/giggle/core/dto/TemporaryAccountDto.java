package com.inglo.giggle.core.dto;

import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.Builder;

@Builder
public record TemporaryAccountDto(
        String id,
        String email,
        String password,
        ESecurityRole accountType
) {
    public static TemporaryAccountDto fromEntity(TemporaryAccount temporaryAccount) {
        return new TemporaryAccountDto(
                temporaryAccount.getId(),
                temporaryAccount.getEmail(),
                temporaryAccount.getPassword(),
                temporaryAccount.getAccountType()
        );
    }
}
