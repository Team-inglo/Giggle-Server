package com.inglo.giggle.term.persistence.mapper;

import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.persistence.entity.TermAccountEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class TermAccountMapper {
    public static TermAccount toDomain(TermAccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return TermAccount.builder()
                .id(entity.getId())
                .isAccepted(entity.getIsAccepted())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .accountId(isInitialized(entity.getAccountEntity()) ? entity.getAccountEntity().getId() : null)
                .termId(isInitialized(entity.getTermEntity()) ? entity.getTermEntity().getId() : null)
                .build();
    }

    public static TermAccountEntity toEntity(TermAccount domain) {
        if (domain == null) {
            return null;
        }

        return TermAccountEntity.builder()
                .isAccepted(domain.getIsAccepted())
                .build();
    }

    public static List<TermAccount> toDomains(List<TermAccountEntity> entities) {
        return entities.stream()
                .map(TermAccountMapper::toDomain)
                .toList();
    }

    public static List<TermAccountEntity> toEntities(List<TermAccount> domains) {
        return domains.stream()
                .map(TermAccountMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
