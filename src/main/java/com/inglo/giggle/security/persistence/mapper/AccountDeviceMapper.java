package com.inglo.giggle.security.persistence.mapper;

import com.inglo.giggle.security.domain.AccountDevice;
import com.inglo.giggle.security.persistence.entity.mysql.AccountDeviceEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class AccountDeviceMapper {

    public static AccountDevice toDomain(AccountDeviceEntity entity) {
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
                .accountId(isInitialized(entity.getAccountEntity()) ? entity.getAccountEntity().getId() : null)
                .build();
    }

    public static AccountDeviceEntity toEntity(AccountDevice domain) {
        if (domain == null) {
            return null;
        }
        return AccountDeviceEntity.builder()
                .deviceId(domain.getDeviceId())
                .deviceToken(domain.getDeviceToken())
                .build();
    }

    public static List<AccountDevice> toDomains(List<AccountDeviceEntity> entities) {
        return entities.stream()
                .map(AccountDeviceMapper::toDomain)
                .toList();
    }

    public static List<AccountDeviceEntity> toEntities(List<AccountDevice> domains) {
        return domains.stream()
                .map(AccountDeviceMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
