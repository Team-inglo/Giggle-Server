package com.inglo.giggle.notification.persistence.mapper;

import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.entity.NotificationEntity;

import java.util.List;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .isRead(entity.getIsRead())
                .notificationType(entity.getNotificationType())
                .userOwnerJobPostingId(entity.getUserOwnerJobPostingEntity() != null ? entity.getUserOwnerJobPostingEntity().getId() : null)
                .userOwnerJobPostingInfo(entity.getUserOwnerJobPostingEntity() != null ? Notification.UserOwnerJobPostingInfo.builder()
                        .id(entity.getUserOwnerJobPostingEntity().getId())
                        .jobPostingInfo(entity.getUserOwnerJobPostingEntity().getJobPostingEntity() != null ? Notification.JobPostingInfo.builder()
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
}
