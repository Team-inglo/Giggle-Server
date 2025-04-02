package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;

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
                .ownerId(entity.getOwnerEntity() != null ? entity.getOwnerEntity().getId() : null)
                .workDayTimes(PostingWorkDayTimeMapper.toDomains(entity.getWorkDayTimes()))
                .companyImages(CompanyImageMapper.toDomains(entity.getCompanyImageEntities()))
                .bookMarks(BookMarkMapper.toDomains(entity.getBookMarkEntities()))
                .userOwnerJobPostings(entity.getUserOwnerJobPostingEntities() != null ? UserOwnerJobPostingMapper.toDomains(entity.getUserOwnerJobPostingEntities()) : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .ownerInfo(entity.getOwnerEntity() != null ? JobPosting.OwnerInfo.builder()
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
                .workDayTimes(PostingWorkDayTimeMapper.toEntities(domain.getWorkDayTimes()))
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
}
