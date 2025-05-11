package com.inglo.giggle.security.account.adapter.out.persistence.mapper;

import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountDeviceEntity;
import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.account.domain.AccountDevice;
import org.springframework.stereotype.Component;

@Component
public class AccountDeviceMapper {

    public AccountDevice toDomain(AccountDeviceEntity entity) {
        if (entity == null) {
            return null;
        }
        return AccountDevice.builder()
                .id(entity.getId())
                .deviceId(entity.getDeviceId())
                .deviceToken(entity.getDeviceToken())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public AccountDeviceEntity toEntity(
            AccountDevice domain,
            AccountEntity accountEntity
    ) {
        if (domain == null) {
            return null;
        }
        return AccountDeviceEntity.builder()
                .id(domain.getId())
                .deviceId(domain.getDeviceId())
                .deviceToken(domain.getDeviceToken())
                .accountEntity(accountEntity)
                .build();
    }
}
