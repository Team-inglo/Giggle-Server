package com.inglo.giggle.security.account.adapter.out.persistence.mapper;

import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.account.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toDomain(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return Account.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .role(entity.getRole())
                .serialId(entity.getSerialId())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public AccountEntity toEntity(Account domain) {
        if (domain == null) {
            return null;
        }

        return AccountEntity.builder()
                .id(domain.getId())
                .provider(domain.getProvider())
                .role(domain.getRole())
                .serialId(domain.getSerialId())
                .password(domain.getPassword())
                .build();
    }
}

