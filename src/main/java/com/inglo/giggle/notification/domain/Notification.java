package com.inglo.giggle.notification.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.type.ENotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notification extends BaseDomain {

    private Long id;
    private String message;
    private Boolean isRead;
    private ENotificationType notificationType;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private Long userOwnerJobPostingId;

    @Builder
    public Notification(
            Long id,
            String message,
            Boolean isRead,
            ENotificationType notificationType,
            Long userOwnerJobPostingId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.userOwnerJobPostingId = userOwnerJobPostingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updateIsRead() {
        this.isRead = true;
    }
}
