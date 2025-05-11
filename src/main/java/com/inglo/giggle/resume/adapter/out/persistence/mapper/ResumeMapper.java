package com.inglo.giggle.resume.adapter.out.persistence.mapper;

import com.inglo.giggle.resume.adapter.out.persistence.entity.EducationEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.WorkExperienceEntity;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResumeMapper {
    public Resume toDomain(
            ResumeEntity resumeEntity,
            List<WorkExperience> workExperiences,
            List<Education> educations,
            LanguageSkill languageSkill
    ) {
        if (resumeEntity == null) {
            return null;
        }
        return Resume.builder()
                .accountId(resumeEntity.getAccountId())
                .introduction(resumeEntity.getIntroduction())
                .workExperiences(workExperiences)
                .educations(educations)
                .languageSkill(languageSkill)
                .build();
    }

    public ResumeEntity toEntity(
            Resume resume,
            List<WorkExperienceEntity> workExperienceEntities,
            List<EducationEntity> educationEntities,
            LanguageSkillEntity languageSkillEntity
    ) {
        if (resume == null) {
            return null;
        }
        return ResumeEntity.builder()
                .accountId(resume.getAccountId())
                .introduction(resume.getIntroduction())
                .workExperienceEntities(workExperienceEntities)
                .educationEntities(educationEntities)
                .languageSkillEntity(languageSkillEntity)
                .build();
    }

    public ResumeEntity toEntity(
            Resume resume
    ) {
        if (resume == null) {
            return null;
        }
        return ResumeEntity.builder()
                .accountId(resume.getAccountId())
                .introduction(resume.getIntroduction())
                .build();
    }
}
