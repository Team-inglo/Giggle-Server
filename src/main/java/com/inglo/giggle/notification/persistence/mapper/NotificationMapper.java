package com.inglo.giggle.notification.persistence.mapper;

import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.entity.NotificationEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .isRead(entity.getIsRead())
                .notificationType(entity.getNotificationType())
                .userOwnerJobPostingId(isInitialized(entity.getUserOwnerJobPostingEntity()) ? entity.getUserOwnerJobPostingEntity().getId() : null)
                .userOwnerJobPostingInfo(isInitialized(entity.getUserOwnerJobPostingEntity()) ? Notification.UserOwnerJobPostingInfo.builder()
                        .id(entity.getUserOwnerJobPostingEntity().getId())
                        .jobPostingInfo(isInitialized(entity.getUserOwnerJobPostingEntity().getJobPostingEntity()) ? Notification.JobPostingInfo.builder()
                                .id(entity.getUserOwnerJobPostingEntity().getJobPostingEntity().getId())
                                .title(entity.getUserOwnerJobPostingEntity().getJobPostingEntity().getTitle())
                                .build() : null)
                        .build() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static NotificationEntity toEntity(Notification domain) {
        return NotificationEntity.builder()
                .message(domain.getMessage())
                .isRead(domain.getIsRead())
                .notificationType(domain.getNotificationType())
                .build();
    }

    public static List<Notification> toDomains(List<NotificationEntity> entities) {
        return entities.stream()
                .map(NotificationMapper::toDomain)
                .toList();
    }

    public static List<NotificationEntity> toEntities(List<Notification> domains) {
        return domains.stream()
                .map(NotificationMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
