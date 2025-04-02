package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;

public class ResumeMapper {
    public static Resume toDomain(ResumeEntity entity) {
        if (entity == null) {
            return null;
        }
        return Resume.builder()
                .accountId(entity.getAccountId())
                .introduction(entity.getIntroduction())
                .workExperiences(WorkExperienceMapper.toDomains(entity.getWorkExperienceEntities()))
                .educations(EducationMapper.toDomains(entity.getEducationEntities()))
                .languageSkill(LanguageSkillMapper.toDomain(entity.getLanguageSkillEntity()))
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
}
