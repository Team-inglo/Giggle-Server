package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;

public class LanguageSkillMapper {
    public static LanguageSkill toDomain(LanguageSkillEntity entity) {
        if (entity == null) {
            return null;
        }
        return LanguageSkill.builder()
                .topikLevel(entity.getTopikLevel())
                .socialIntegrationLevel(entity.getSocialIntegrationLevel())
                .sejongInstituteLevel(entity.getSejongInstituteLevel())
                .additionalLanguages(isInitialized(entity.getAdditionalLanguageEntities()) ? AdditionalLanguageMapper.toDomains(entity.getAdditionalLanguageEntities()) : null)
                .resumeId(isInitialized(entity.getResumeEntity()) ? entity.getResumeEntity().getAccountId() : null)
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

    private static boolean isInitialized(Collection<?> collection) {
        return collection instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) collection).wasInitialized();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
