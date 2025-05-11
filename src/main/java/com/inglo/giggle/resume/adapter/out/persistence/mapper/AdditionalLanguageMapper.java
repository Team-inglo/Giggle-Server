package com.inglo.giggle.resume.adapter.out.persistence.mapper;

import com.inglo.giggle.resume.adapter.out.persistence.entity.AdditionalLanguageEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import org.springframework.stereotype.Component;

@Component
public class AdditionalLanguageMapper {
    public AdditionalLanguage toDomain(
            AdditionalLanguageEntity entity
    ) {
        if (entity == null) {
            return null;
        }
        return AdditionalLanguage.builder()
                .id(entity.getId())
                .languageName(entity.getLanguageName())
                .level(entity.getLevel())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public AdditionalLanguageEntity toEntity(
            AdditionalLanguage domain,
            LanguageSkillEntity languageSkillEntity
    ) {
        if (domain == null) {
            return null;
        }
        return AdditionalLanguageEntity.builder()
                .id(domain.getId())
                .languageName(domain.getLanguageName())
                .level(domain.getLevel())
                .languageSkillEntity(languageSkillEntity)
                .build();
    }
}
