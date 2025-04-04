package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.persistence.entity.AdditionalLanguageEntity;

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
                .languageSkillId(entity.getLanguageSkillsId())
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
                .id(domain.getId())
                .languageName(domain.getLanguageName())
                .level(domain.getLevel())
                .languageSkillsId(domain.getLanguageSkillId())
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
}
