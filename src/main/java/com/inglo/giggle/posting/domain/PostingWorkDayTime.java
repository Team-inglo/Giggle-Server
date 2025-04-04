package com.inglo.giggle.posting.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.type.EDayOfWeek;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class PostingWorkDayTime extends BaseDomain {
    private final Long id;
    private final EDayOfWeek dayOfWeek;
    private final LocalTime workStartTime;
    private final LocalTime workEndTime;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private Long jobPostingId;

    @Builder
    public PostingWorkDayTime(Long id, EDayOfWeek dayOfWeek, LocalTime workStartTime, LocalTime workEndTime, Long jobPostingId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.jobPostingId = jobPostingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
