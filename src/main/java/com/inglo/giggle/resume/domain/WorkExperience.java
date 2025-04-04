package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class WorkExperience extends BaseDomain {

    private Long id;
    private String experienceTitle;
    private String workplace;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID resumeId;

    @Builder
    public WorkExperience(Long id, String experienceTitle, String workplace, LocalDate startDate, LocalDate endDate, String description, UUID resumeId,
                          LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.experienceTitle = experienceTitle;
        this.workplace = workplace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.resumeId = resumeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void checkValidation(UUID accountId) {
        if (!resumeId.equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateSelf(
            String experienceTitle,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description
    ) {
        this.experienceTitle = experienceTitle;
        this.workplace = workplace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
