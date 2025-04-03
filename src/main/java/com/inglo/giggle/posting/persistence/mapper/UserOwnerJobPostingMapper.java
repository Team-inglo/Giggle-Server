package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.document.persistence.mapper.DocumentMapper;
import com.inglo.giggle.notification.persistence.mapper.NotificationMapper;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class UserOwnerJobPostingMapper {
    public static UserOwnerJobPosting toDomain(UserOwnerJobPostingEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserOwnerJobPosting.builder()
                .id(entity.getId())
                .step(entity.getStep())
                .lastStepUpdated(entity.getLastStepUpdated())
                .result(entity.getResult())
                .feedback(entity.getFeedback())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .userId(isInitialized(entity.getUserEntity()) ? entity.getUserEntity().getId() : null)
                .ownerId(isInitialized(entity.getOwnerEntity()) ? entity.getOwnerEntity().getId() : null)
                .jobPostingId(isInitialized(entity.getJobPostingEntity()) ? entity.getJobPostingEntity().getId() : null)
                .documents(isInitialized(entity.getDocumentEntities()) ? DocumentMapper.toDomains(entity.getDocumentEntities()) : null)
                .notifications(isInitialized(entity.getNotificationEntities()) ? NotificationMapper.toDomains(entity.getNotificationEntities()) : null)
                .userInfo(isInitialized(entity.getUserEntity()) ? UserOwnerJobPosting.UserInfo.builder()
                        .id(entity.getUserEntity().getId())
                        .profileImgUrl(entity.getUserEntity().getProfileImgUrl())
                        .serialId(entity.getUserEntity().getSerialId())
                        .firstName(entity.getUserEntity().getFirstName())
                        .lastName(entity.getUserEntity().getLastName())
                        .name(entity.getUserEntity().getName())
                        .phoneNumber(entity.getUserEntity().getPhoneNumber())
                        .nationality(entity.getUserEntity().getNationality())
                        .gender(entity.getUserEntity().getGender())
                        .visa(entity.getUserEntity().getVisa())
                        .birth(entity.getUserEntity().getBirth())
                        .email(entity.getUserEntity().getEmail())
                        .address(AddressMapper.toDomain(entity.getUserEntity().getAddressEntity()))
                        .notificationAllowed(entity.getUserEntity().getNotificationAllowed())
                        .build() : null)
                .ownerInfo(isInitialized(entity.getOwnerEntity()) ? UserOwnerJobPosting.OwnerInfo.builder()
                        .id(entity.getOwnerEntity().getId())
                        .profileImgUrl(entity.getOwnerEntity().getProfileImgUrl())
                        .companyName(entity.getOwnerEntity().getCompanyName())
                        .notificationAllowed(entity.getOwnerEntity().getNotificationAllowed())
                        .build() : null)
                .jobPostingInfo(isInitialized(entity.getJobPostingEntity()) ? UserOwnerJobPosting.JobPostingInfo.builder()
                        .id(entity.getJobPostingEntity().getId())
                        .title(entity.getJobPostingEntity().getTitle())
                        .addressName(entity.getJobPostingEntity().getAddressEntity().getAddressName())
                        .hourlyRate(entity.getJobPostingEntity().getHourlyRate())
                        .workPeriod(entity.getJobPostingEntity().getWorkPeriod())
                        .workDayTimes(isInitialized(entity.getJobPostingEntity().getWorkDayTimeEntities()) ? PostingWorkDayTimeMapper.toDomains(entity.getJobPostingEntity().getWorkDayTimeEntities()) : null)
                        .recruiterName(entity.getJobPostingEntity().getRecruiterName())
                        .recruiterPhoneNumber(entity.getJobPostingEntity().getRecruiterPhoneNumber())
                        .build() : null)
                .build();
    }

    public static UserOwnerJobPostingEntity toEntity(UserOwnerJobPosting domain) {
        if (domain == null) {
            return null;
        }
        return UserOwnerJobPostingEntity.builder()
                .step(domain.getStep())
                .lastStepUpdated(domain.getLastStepUpdated())
                .result(domain.getResult())
                .feedback(domain.getFeedback())
                .memo(domain.getMemo())
                .build();
    }

    public static List<UserOwnerJobPosting> toDomains(List<UserOwnerJobPostingEntity> entities) {
        return entities.stream()
                .map(UserOwnerJobPostingMapper::toDomain)
                .toList();
    }

    public static List<UserOwnerJobPostingEntity> toEntities(List<UserOwnerJobPosting> domains) {
        return domains.stream()
                .map(UserOwnerJobPostingMapper::toEntity)
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