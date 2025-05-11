package com.inglo.giggle.notification.adapter.out.persistence.mapper;

import com.inglo.giggle.notification.adapter.out.persistence.entity.NotificationEntity;
import com.inglo.giggle.notification.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .isRead(entity.getIsRead())
                .notificationType(entity.getNotificationType())
                .userOwnerJobPostingId(entity.getUserOwnerJobPostingId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public NotificationEntity toEntity(Notification domain) {
        return NotificationEntity.builder()
                .id(domain.getId())
                .message(domain.getMessage())
                .isRead(domain.getIsRead())
                .notificationType(domain.getNotificationType())
                .userOwnerJobPostingId(domain.getUserOwnerJobPostingId())
                .build();
    }
}
