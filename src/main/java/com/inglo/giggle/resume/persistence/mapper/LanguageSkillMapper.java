package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;

public class LanguageSkillMapper {
    public static LanguageSkill toDomain(LanguageSkillEntity entity) {
        if (entity == null) {
            return null;
        }
        return LanguageSkill.builder()
                .topikLevel(entity.getTopikLevel())
                .socialIntegrationLevel(entity.getSocialIntegrationLevel())
                .sejongInstituteLevel(entity.getSejongInstituteLevel())
                .additionalLanguages(entity.getAdditionalLanguageEntities() != null ? AdditionalLanguageMapper.toDomains(entity.getAdditionalLanguageEntities()) : null)
                .resumeId(entity.getResumeEntity() != null ? entity.getResumeEntity().getAccountId() : null)
                .build();
    }

    public static LanguageSkillEntity toEntity(LanguageSkill domain) {
        if (domain == null) {
            return null;
        }
        return LanguageSkillEntity.builder()
                .topikLevel(domain.getTopikLevel())
                .socialIntegrationLevel(domain.getSocialIntegrationLevel())
                .sejongInstituteLevel(domain.getSejongInstituteLevel())
                .additionalLanguageEntities(domain.getAdditionalLanguages() != null ? AdditionalLanguageMapper.toEntities(domain.getAdditionalLanguages()) : null)
                .build();
    }
}
