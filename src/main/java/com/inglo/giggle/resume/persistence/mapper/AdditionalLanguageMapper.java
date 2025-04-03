package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.persistence.entity.AdditionalLanguageEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class AdditionalLanguageMapper {
    public static AdditionalLanguage toDomain(AdditionalLanguageEntity entity) {
        if (entity == null) {
            return null;
        }
        return AdditionalLanguage.builder()
                .id(entity.getId())
                .languageName(entity.getLanguageName())
                .level(entity.getLevel())
                .languageSkillId(isInitialized(entity.getLanguageSkillEntity()) ? entity.getLanguageSkillEntity().getResumeId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static AdditionalLanguageEntity toEntity(AdditionalLanguage domain) {
        if (domain == null) {
            return null;
        }
        return AdditionalLanguageEntity.builder()
                .languageName(domain.getLanguageName())
                .level(domain.getLevel())
                .build();
    }

    public static List<AdditionalLanguage> toDomains(List<AdditionalLanguageEntity> entities) {
        return entities.stream()
                .map(AdditionalLanguageMapper::toDomain)
                .toList();
    }

    public static List<AdditionalLanguageEntity> toEntities(List<AdditionalLanguage> domains) {
        return domains.stream()
                .map(AdditionalLanguageMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
