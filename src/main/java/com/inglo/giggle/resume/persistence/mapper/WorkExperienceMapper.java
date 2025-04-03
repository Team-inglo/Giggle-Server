package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.entity.WorkExperienceEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class WorkExperienceMapper {
    public static WorkExperience toDomain(WorkExperienceEntity entity) {
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
                .resumeId(isInitialized(entity.getResumeEntity()) ? entity.getResumeEntity().getAccountId() : null)
                .build();
    }

    public static WorkExperienceEntity toEntity(WorkExperience domain) {
        if (domain == null) {
            return null;
        }
        return WorkExperienceEntity.builder()
                .experienceTitle(domain.getExperienceTitle())
                .workplace(domain.getWorkplace())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .description(domain.getDescription())
                .build();
    }

    public static List<WorkExperience> toDomains(List<WorkExperienceEntity> entities) {
        return entities.stream()
                .map(WorkExperienceMapper::toDomain)
                .toList();
    }

    public static List<WorkExperienceEntity> toEntities(List<WorkExperience> domains) {
        return domains.stream()
                .map(WorkExperienceMapper::toEntity)
                .toList();
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
