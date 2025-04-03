package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;

public class ResumeMapper {
    public static Resume toDomain(ResumeEntity entity) {
        if (entity == null) {
            return null;
        }
        return Resume.builder()
                .accountId(entity.getAccountId())
                .introduction(entity.getIntroduction())
                .workExperiences(isInitialized(entity.getWorkExperienceEntities()) ? WorkExperienceMapper.toDomains(entity.getWorkExperienceEntities()) : null)
                .educations(isInitialized(entity.getEducationEntities()) ? EducationMapper.toDomains(entity.getEducationEntities()) : null)
                .languageSkill(isInitialized(entity.getLanguageSkillEntity()) ? LanguageSkillMapper.toDomain(entity.getLanguageSkillEntity()) : null)
                .build();
    }

    public static ResumeEntity toEntity(Resume domain) {
        if (domain == null) {
            return null;
        }
        return ResumeEntity.builder()
                .introduction(domain.getIntroduction())
                .workExperienceEntities(WorkExperienceMapper.toEntities(domain.getWorkExperiences()))
                .educationEntities(EducationMapper.toEntities(domain.getEducations()))
                .languageSkillEntity(LanguageSkillMapper.toEntity(domain.getLanguageSkill()))
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
