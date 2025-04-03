package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.persistence.entity.BookMarkEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class BookMarkMapper {
    public static BookMark toDomain(BookMarkEntity entity) {
        if (entity == null) {
            return null;
        }
        return BookMark.builder()
                .id(entity.getId())
                .userId(isInitialized(entity.getUserEntity()) ? entity.getUserEntity().getId() : null)
                .jobPostingId(isInitialized(entity.getJobPostingEntity()) ? entity.getJobPostingEntity().getId() : null)
                .jobPostingInfo(isInitialized(entity.getJobPostingEntity()) ?
                        BookMark.JobPostingInfo.builder()
                                .id(entity.getJobPostingEntity().getId())
                                .title(entity.getJobPostingEntity().getTitle())
                                .hourlyRate(entity.getJobPostingEntity().getHourlyRate())
                                .recruitmentDeadLine(entity.getJobPostingEntity().getRecruitmentDeadLine())
                                .address(AddressMapper.toDomain(entity.getJobPostingEntity().getAddressEntity()))
                                .workPeriod(entity.getJobPostingEntity().getWorkPeriod())
                                .workDaysPerWeek(entity.getJobPostingEntity().getWorkDaysPerWeekToString())
                                .visa(entity.getJobPostingEntity().getVisa())
                                .jobCategory(entity.getJobPostingEntity().getJobCategory())
                                .createdAt(entity.getJobPostingEntity().getCreatedAt())
                                .ownerInfo(isInitialized(entity.getJobPostingEntity().getOwnerEntity()) ?
                                        BookMark.JobPostingInfo.OwnerInfo.builder()
                                                .profileImgUrl(entity.getJobPostingEntity().getOwnerEntity().getProfileImgUrl())
                                                .build()
                                        : null)
                                .build() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static BookMarkEntity toEntity(BookMark domain) {
        if (domain == null) {
            return null;
        }
        return BookMarkEntity.builder()
                .build();
    }

    public static List<BookMark> toDomains(List<BookMarkEntity> entities) {
        return entities.stream()
                .map(BookMarkMapper::toDomain)
                .toList();
    }

    public static List<BookMarkEntity> toEntities(List<BookMark> domains) {
        return domains.stream()
                .map(BookMarkMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
