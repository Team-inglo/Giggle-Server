package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class JobPostingMapper {
    public static JobPosting toDomain(JobPostingEntity entity) {
        if (entity == null) {
            return null;
        }
        return JobPosting.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .jobCategory(entity.getJobCategory())
                .hourlyRate(entity.getHourlyRate())
                .recruitmentDeadLine(entity.getRecruitmentDeadLine())
                .workPeriod(entity.getWorkPeriod())
                .recruitmentNumber(entity.getRecruitmentNumber())
                .gender(entity.getGender())
                .ageRestriction(entity.getAgeRestriction())
                .educationLevel(entity.getEducationLevel())
                .visa(entity.getVisa())
                .recruiterName(entity.getRecruiterName())
                .recruiterEmail(entity.getRecruiterEmail())
                .recruiterPhoneNumber(entity.getRecruiterPhoneNumber())
                .description(entity.getDescription())
                .preferredConditions(entity.getPreferredConditions())
                .employmentType(entity.getEmploymentType())
                .address(AddressMapper.toDomain(entity.getAddressEntity()))
                .ownerId(isInitialized(entity.getOwnerEntity()) ? entity.getOwnerEntity().getId() : null)
                .workDayTimes(isInitialized(entity.getWorkDayTimeEntities()) ? PostingWorkDayTimeMapper.toDomains(entity.getWorkDayTimeEntities()) : null)
                .companyImages(isInitialized(entity.getCompanyImageEntities()) ? CompanyImageMapper.toDomains(entity.getCompanyImageEntities()) : null)
                .bookMarks(isInitialized(entity.getBookMarkEntities()) ? BookMarkMapper.toDomains(entity.getBookMarkEntities()) : null)
                .userOwnerJobPostings(isInitialized(entity.getUserOwnerJobPostingEntities()) ? UserOwnerJobPostingMapper.toDomains(entity.getUserOwnerJobPostingEntities()) : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .ownerInfo(isInitialized(entity.getOwnerEntity()) ? JobPosting.OwnerInfo.builder()
                        .id(entity.getOwnerEntity().getId())
                        .profileImgUrl(entity.getOwnerEntity().getProfileImgUrl())
                        .companyName(entity.getOwnerEntity().getCompanyName())
                        .name(entity.getOwnerEntity().getName())
                        .address(AddressMapper.toDomain(entity.getOwnerEntity().getAddressEntity()))
                        .build() : null
                )
                .build();
    }

    public static JobPostingEntity toEntity(JobPosting domain) {
        if (domain == null) {
            return null;
        }
        return JobPostingEntity.builder()
                .title(domain.getTitle())
                .jobCategory(domain.getJobCategory())
                .hourlyRate(domain.getHourlyRate())
                .recruitmentDeadLine(domain.getRecruitmentDeadLine())
                .workPeriod(domain.getWorkPeriod())
                .recruitmentNumber(domain.getRecruitmentNumber())
                .gender(domain.getGender())
                .ageRestriction(domain.getAgeRestriction())
                .educationLevel(domain.getEducationLevel())
                .visa(domain.getVisa())
                .recruiterName(domain.getRecruiterName())
                .recruiterEmail(domain.getRecruiterEmail())
                .recruiterPhoneNumber(domain.getRecruiterPhoneNumber())
                .description(domain.getDescription())
                .preferredConditions(domain.getPreferredConditions())
                .employmentType(domain.getEmploymentType())
                .addressEntity(AddressMapper.toEntity(domain.getAddress()))
                .workDayTimeEntities(PostingWorkDayTimeMapper.toEntities(domain.getWorkDayTimes()))
                .companyImageEntities(CompanyImageMapper.toEntities(domain.getCompanyImages()))
                .bookMarkEntities(BookMarkMapper.toEntities(domain.getBookMarks()))
                .build();
    }

    public static List<JobPosting> toDomains(List<JobPostingEntity> entities) {
        return entities.stream()
                .map(JobPostingMapper::toDomain)
                .toList();
    }

    public static List<JobPostingEntity> toEntities(List<JobPosting> domains) {
        return domains.stream()
                .map(JobPostingMapper::toEntity)
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
