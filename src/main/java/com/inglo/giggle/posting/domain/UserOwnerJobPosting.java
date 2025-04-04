package com.inglo.giggle.posting.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UserOwnerJobPosting extends BaseDomain {
    private Long id;
    private EApplicationStep step;
    private LocalDate lastStepUpdated;
    private Boolean result;
    private String feedback;
    private String memo;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID userId;
    private UUID ownerId;
    private Long jobPostingId;

    @Builder
    public UserOwnerJobPosting(Long id, EApplicationStep step, LocalDate lastStepUpdated, Boolean result,
                               String feedback, String memo,
                               UUID userId, UUID ownerId, Long jobPostingId,
                               LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.step = step;
        this.lastStepUpdated = lastStepUpdated;
        this.result = result;
        this.feedback = feedback;
        this.memo = memo;
        this.userId = userId;
        this.ownerId = ownerId;
        this.jobPostingId = jobPostingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updateStepFromResumeUnderReview(boolean isAccepted) {
        if (isAccepted) {
            this.step = EApplicationStep.WAITING_FOR_INTERVIEW;
            this.lastStepUpdated = LocalDate.now();
        } else {
            this.step = EApplicationStep.RESUME_REJECTED;
            this.result = false;
            this.lastStepUpdated = LocalDate.now();
        }
    }

    public void updateStepFromStepWaitingForInterview() {
        this.step = EApplicationStep.FILLING_OUT_DOCUMENTS;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateStepFromStepFillingOutDocument() {
        this.step = EApplicationStep.DOCUMENT_UNDER_REVIEW;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateStepFromStepApplicationInProgress() {
        this.step = EApplicationStep.REGISTERING_RESULTS;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateStepFromStepDocumentUnderReview() {
        this.step = EApplicationStep.APPLICATION_IN_PROGRESS;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateFinalResult(boolean isApproved, String feedback) {
        if (isApproved) {
            this.step = EApplicationStep.APPLICATION_SUCCESS;
            this.result = true;
            this.lastStepUpdated = LocalDate.now();
        } else {
            this.step = EApplicationStep.APPLICATION_REJECTED;
            this.result = false;
            this.lastStepUpdated = LocalDate.now();
        }
        this.feedback = feedback;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}

