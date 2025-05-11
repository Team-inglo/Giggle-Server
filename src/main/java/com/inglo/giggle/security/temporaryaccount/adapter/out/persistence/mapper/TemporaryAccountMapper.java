package com.inglo.giggle.security.temporaryaccount.adapter.out.persistence.mapper;

import com.inglo.giggle.security.temporaryaccount.adapter.out.persistence.entity.TemporaryAccountEntity;
import com.inglo.giggle.security.temporaryaccount.domain.TemporaryAccount;
import org.springframework.stereotype.Component;

@Component
public class TemporaryAccountMapper {

    public TemporaryAccount toDomain(TemporaryAccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return TemporaryAccount.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .accountType(entity.getAccountType())
                .build();
    }

    public TemporaryAccountEntity toEntity(TemporaryAccount domain) {
        if (domain == null) {
            return null;
        }

        return TemporaryAccountEntity.builder()
                .email(domain.getEmail())
                .password(domain.getPassword())
                .accountType(domain.getAccountType())
                .build();
    }
}
