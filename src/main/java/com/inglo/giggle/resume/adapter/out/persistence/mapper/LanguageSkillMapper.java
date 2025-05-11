package com.inglo.giggle.resume.adapter.out.persistence.mapper;

import com.inglo.giggle.resume.adapter.out.persistence.entity.AdditionalLanguageEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.LanguageSkill;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LanguageSkillMapper {
    public LanguageSkill toDomain(
            LanguageSkillEntity languageSkillEntity,
            List<AdditionalLanguage> additionalLanguages
    ) {
        if (languageSkillEntity == null) {
            return null;
        }
        return LanguageSkill.builder()
                .id(languageSkillEntity.getResumeId())
                .topikLevel(languageSkillEntity.getTopikLevel())
                .socialIntegrationLevel(languageSkillEntity.getSocialIntegrationLevel())
                .sejongInstituteLevel(languageSkillEntity.getSejongInstituteLevel())
                .additionalLanguages(additionalLanguages)
                .build();
    }

    public LanguageSkillEntity toEntity(
            LanguageSkill domain,
            List<AdditionalLanguageEntity> additionalLanguageEntities,
            ResumeEntity resumeEntity
    ) {
        if (domain == null) {
            return null;
        }
        return LanguageSkillEntity.builder()
                .resumeId(domain.getId())
                .topikLevel(domain.getTopikLevel())
                .socialIntegrationLevel(domain.getSocialIntegrationLevel())
                .sejongInstituteLevel(domain.getSejongInstituteLevel())
                .additionalLanguageEntities(additionalLanguageEntities)
                .resumeEntity(resumeEntity)
                .build();
    }

    public LanguageSkillEntity toEntity(
            LanguageSkill domain
    ) {
        if (domain == null) {
            return null;
        }
        return LanguageSkillEntity.builder()
                .resumeId(domain.getId())
                .topikLevel(domain.getTopikLevel())
                .socialIntegrationLevel(domain.getSocialIntegrationLevel())
                .sejongInstituteLevel(domain.getSejongInstituteLevel())
                .build();
    }
}
