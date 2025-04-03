package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.persistence.entity.EducationEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
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
                .resumeId(isInitialized(entity.getResumeEntity()) ? entity.getResumeEntity().getAccountId() : null)
                .schoolId(isInitialized(entity.getSchoolEntity()) ? entity.getSchoolEntity().getId() : null)
                .schoolInfo(isInitialized(entity.getSchoolEntity()) ? Education.SchoolInfo.builder()
                        .id(entity.getSchoolEntity().getId())
                        .schoolName(entity.getSchoolEntity().getSchoolName())
                        .isMetropolitan(entity.getSchoolEntity().getIsMetropolitan())
                        .instituteName(entity.getSchoolEntity().getInstituteName())
                        .coordinatorName(entity.getSchoolEntity().getCoordinatorName())
                        .coordinatorPhoneNumber(entity.getSchoolEntity().getCoordinatorPhoneNumber())
                        .address(AddressMapper.toDomain(entity.getSchoolEntity().getAddressEntity()))
                        .build() : null)
                .build();
    }

    public static EducationEntity toEntity(Education domain) {
        if (domain == null) {
            return null;
        }
        return EducationEntity.builder()
                .educationLevel(domain.getEducationLevel())
                .major(domain.getMajor())
                .gpa(domain.getGpa())
                .enrollmentDate(domain.getEnrollmentDate())
                .graduationDate(domain.getGraduationDate())
                .grade(domain.getGrade())
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

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
