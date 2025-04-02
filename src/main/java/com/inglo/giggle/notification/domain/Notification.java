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

    /* -------------------------------------------- */
    /* Nested Class ------------------------------- */
    /* -------------------------------------------- */
    private UserOwnerJobPostingInfo userOwnerJobPostingInfo;

    @Builder
    public Notification(
            Long id,
            String message,
            Boolean isRead,
            ENotificationType notificationType,
            Long userOwnerJobPostingId,
            UserOwnerJobPostingInfo userOwnerJobPostingInfo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.userOwnerJobPostingId = userOwnerJobPostingId;
        this.userOwnerJobPostingInfo = userOwnerJobPostingInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Getter
    public static class UserOwnerJobPostingInfo {
        private Long id;
        private JobPostingInfo jobPostingInfo;

        @Builder
        public UserOwnerJobPostingInfo(
                Long id,
                JobPostingInfo jobPostingInfo
        ) {
            this.id = id;
            this.jobPostingInfo = jobPostingInfo;
        }
    }

    @Getter
    public static class JobPostingInfo {
        private Long id;
        private String title;

        @Builder
        public JobPostingInfo(
                Long id,
                String title
        ) {
            this.id = id;
            this.title = title;
        }
    }

    public void updateIsRead() {
        this.isRead = true;
    }
}
