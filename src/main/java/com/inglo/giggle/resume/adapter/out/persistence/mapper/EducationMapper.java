package com.inglo.giggle.resume.adapter.out.persistence.mapper;

import com.inglo.giggle.resume.adapter.out.persistence.entity.EducationEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.domain.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {
    public Education toDomain(
            EducationEntity entity
    ) {
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
                .schoolId(entity.getSchoolId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public EducationEntity toEntity(
            Education domain,
            ResumeEntity resumeEntity
    ) {
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
                .resumeEntity(resumeEntity)
                .schoolId(domain.getSchoolId())
                .build();
    }
}
