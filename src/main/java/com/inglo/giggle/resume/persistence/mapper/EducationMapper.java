package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.persistence.entity.EducationEntity;

import java.util.List;

public class EducationMapper {
    public static Education toDomain(EducationEntity entity) {
        if (entity == null) {
            return null;
        }
        return Education.builder()
                .id(entity.getId())
                .educationLevel(entity.getEducationLevel())
                .major(entity.getMajor())
                .gpa(entity.getGpa())
                .enrollmentDate(entity.getEnrollmentDate())
                .graduationDate(entity.getGraduationDate())
                .grade(entity.getGrade())
                .resumeId(entity.getResumeId())
                .schoolId(entity.getSchoolId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static EducationEntity toEntity(Education domain) {
        if (domain == null) {
            return null;
        }
        return EducationEntity.builder()
                .id(domain.getId())
                .educationLevel(domain.getEducationLevel())
                .major(domain.getMajor())
                .gpa(domain.getGpa())
                .enrollmentDate(domain.getEnrollmentDate())
                .graduationDate(domain.getGraduationDate())
                .grade(domain.getGrade())
                .resumeId(domain.getResumeId())
                .schoolId(domain.getSchoolId())
                .build();
    }

    public static List<Education> toDomains(List<EducationEntity> entities) {
        return entities.stream()
                .map(EducationMapper::toDomain)
                .toList();
    }

    public static List<EducationEntity> toEntities(List<Education> domains) {
        return domains.stream()
                .map(EducationMapper::toEntity)
                .toList();
    }
}
