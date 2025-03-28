package com.inglo.giggle.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.notification.domain.Notification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ReadNotificationOverviewResponseDto extends SelfValidating<ReadNotificationOverviewResponseDto> {

    @NotNull(message = "has_next는 null이 될 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("notification_list")
    @Valid
    private final List<NotificationDto> notificationList;

    @Builder
    public ReadNotificationOverviewResponseDto(Boolean hasNext, List<NotificationDto> notificationList) {
        this.hasNext = hasNext;
        this.notificationList = notificationList;
        this.validateSelf();
    }

    public static ReadNotificationOverviewResponseDto fromPages(Page<Notification> notificationList) {
        return ReadNotificationOverviewResponseDto.builder()
                .hasNext(notificationList.hasNext())
                .notificationList(notificationList.map(
                        NotificationDto::fromEntity
                ).getContent())
                .build();
    }

    @Getter
    public static class NotificationDto extends SelfValidating<NotificationDto> {

        @NotNull(message = "id는 null이 될 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "user_owner_job_posting_id는 null이 될 수 없습니다.")
        @JsonProperty("user_owner_job_posting_id")
        private final Long userOwnerJobPostingId;

        @NotNull(message = "title은 null이 될 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @NotNull(message = "description은 null이 될 수 없습니다.")
        @JsonProperty("description")
        private final String description;

        @NotNull(message = "is_read는 null이 될 수 없습니다.")
        @JsonProperty("is_read")
        private final Boolean isRead;

        @NotNull(message = "created_at는 null이 될 수 없습니다.")
        @JsonProperty("created_at")
        private final String createdAt;

        @Builder
        public NotificationDto(Long id, Long userOwnerJobPostingId, String title, String description, Boolean isRead, String createdAt) {
            this.id = id;
            this.userOwnerJobPostingId = userOwnerJobPostingId;
            this.title = title;
            this.description = description;
            this.isRead = isRead;
            this.createdAt = createdAt;
            this.validateSelf();
        }

        public static NotificationDto fromEntity(Notification notification) {
            return NotificationDto.builder()
                    .id(notification.getId())
                    .userOwnerJobPostingId(notification.getUserOwnerJobPosting().getId())
                    .title(notification.getUserOwnerJobPosting().getJobPosting().getTitle())
                    .description(notification.getMessage())
                    .isRead(notification.getIsRead())
                    .createdAt(DateTimeUtil.formatNotificationTime(notification.getCreatedAt()))
                    .build();
        }
    }
}
