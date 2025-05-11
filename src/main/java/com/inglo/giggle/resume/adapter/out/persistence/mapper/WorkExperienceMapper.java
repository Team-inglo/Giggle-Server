package com.inglo.giggle.resume.adapter.out.persistence.mapper;

import com.inglo.giggle.resume.adapter.out.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.WorkExperienceEntity;
import com.inglo.giggle.resume.domain.WorkExperience;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceMapper {
    public WorkExperience toDomain(
            WorkExperienceEntity entity
    ) {
        if (entity == null) {
            return null;
        }
        return WorkExperience.builder()
                .id(entity.getId())
                .experienceTitle(entity.getExperienceTitle())
                .workplace(entity.getWorkplace())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public WorkExperienceEntity toEntity(
            WorkExperience domain,
            ResumeEntity resumeEntity
    ) {
        if (domain == null) {
            return null;
        }
        return WorkExperienceEntity.builder()
                .id(domain.getId())
                .experienceTitle(domain.getExperienceTitle())
                .workplace(domain.getWorkplace())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .description(domain.getDescription())
                .resumeEntity(resumeEntity)
                .build();
    }
}
