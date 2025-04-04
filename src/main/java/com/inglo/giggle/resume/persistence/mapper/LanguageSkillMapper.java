package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;

public class LanguageSkillMapper {
    public static LanguageSkill toDomain(LanguageSkillEntity entity) {
        if (entity == null) {
            return null;
        }
        return LanguageSkill.builder()
                .resumeId(entity.getResumeId())
                .topikLevel(entity.getTopikLevel())
                .socialIntegrationLevel(entity.getSocialIntegrationLevel())
                .sejongInstituteLevel(entity.getSejongInstituteLevel())
                .build();
    }

    public static LanguageSkillEntity toEntity(LanguageSkill domain) {
        if (domain == null) {
            return null;
        }
        return LanguageSkillEntity.builder()
                .resumeId(domain.getResumeId())
                .topikLevel(domain.getTopikLevel())
                .socialIntegrationLevel(domain.getSocialIntegrationLevel())
                .sejongInstituteLevel(domain.getSejongInstituteLevel())
                .build();
    }
}
