package com.inglo.giggle.posting.domain;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserOwnerJobPostingAggregate {

    UserOwnerJobPosting userOwnerJobPosting;

    User user;

    Owner owner;

    JobPosting jobPosting;

    @Builder
    public UserOwnerJobPostingAggregate(UserOwnerJobPosting userOwnerJobPosting, User user, Owner owner, JobPosting jobPosting) {
        this.userOwnerJobPosting = userOwnerJobPosting;
        this.user = user;
        this.owner = owner;
        this.jobPosting = jobPosting;
    }

    public void checkUserUserOwnerJobPostingValidation(UUID accountId) {
        if (!user.getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkOwnerUserOwnerJobPostingValidation(UUID ownerId) {
        owner.validateOwnerOwn(ownerId);
    }

    public void updateUserOwnerJobPostingStepFromResumeUnderReview(boolean isAccepted) {
        userOwnerJobPosting.updateStepFromResumeUnderReview(isAccepted);
    }

    public void updateUserOwnerJobPostingStepFromStepWaitingForInterview() {
        userOwnerJobPosting.updateStepFromStepWaitingForInterview();
    }

    public void updateUserOwnerJobPostingStepFromFillingOutDocuments() {
        userOwnerJobPosting.updateStepFromStepFillingOutDocument();
    }

    public void updateUserOwnerJobPostingStepFromStepApplicationInProgress() {
        userOwnerJobPosting.updateStepFromStepApplicationInProgress();
    }

    public void updateUserOwnerJobPostingStepFromStepDocumentUnderReview() {
        userOwnerJobPosting.updateStepFromStepDocumentUnderReview();
    }

    public void updateUserOwnerJobPostingUpdateFinalResult(boolean isAccepted, String feedback) {
        userOwnerJobPosting.updateFinalResult(isAccepted, feedback);
    }

    public void updateUserOwnerJobPostingMemo(String memo) {
        userOwnerJobPosting.updateMemo(memo);
    }
}
