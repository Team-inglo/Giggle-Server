package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserOwnerJobPostingService {

    /* -------------------------------------------- */
    /* Public Method ------------------------------ */
    /* -------------------------------------------- */

    public UserOwnerJobPosting createUserOwnerJobPosting(User user, JobPosting jobPosting, Owner owner) {
        return UserOwnerJobPosting.builder()
                .user(user)
                .jobPosting(jobPosting)
                .owner(owner)
                .build();
    }

    public Integer getSuccessFulHireCounts(List<UserOwnerJobPosting> userOwnerJobPostingList) {
        return Math.toIntExact(userOwnerJobPostingList.stream()
                .filter(userOwnerJobPosting -> userOwnerJobPosting.getStep().equals(EApplicationStep.APPLICATION_SUCCESS))
                .count());
    }

    public void updateStepFromResumeUnderReview(UserOwnerJobPosting userOwnerJobPosting, boolean isAccepted) {
        userOwnerJobPosting.updateStep(isAccepted ? EApplicationStep.WAITING_FOR_INTERVIEW : EApplicationStep.RESUME_REJECTED);
        if(!isAccepted){
            updateResult(userOwnerJobPosting, false);
        }
    }

    public void updateStepFromStepWaitingForInterview(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPosting.updateStep(EApplicationStep.FILLING_OUT_DOCUMENTS);
    }

    public void updateStepFromStepFillingOutDocument(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPosting.updateStep(EApplicationStep.DOCUMENT_UNDER_REVIEW);
    }

    public void updateStepFromStepDocumentUnderReview(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPosting.updateStep(EApplicationStep.APPLICATION_IN_PROGRESS);
    }

    public void updateStepFromStepApplicationInProgress(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPosting.updateStep(EApplicationStep.REGISTERING_RESULTS);
    }

    public void updateStepFromStepRegisteringResult(UserOwnerJobPosting userOwnerJobPosting, boolean isApproved) {
        userOwnerJobPosting.updateStep(isApproved ? EApplicationStep.APPLICATION_SUCCESS : EApplicationStep.APPLICATION_REJECTED);
    }

    public void updateFinalResult(UserOwnerJobPosting userOwnerJobPosting, boolean isApproved, String feedback) {
        updateStepFromStepRegisteringResult(userOwnerJobPosting, isApproved);
        saveFeedback(userOwnerJobPosting, feedback);
        updateResult(userOwnerJobPosting, isApproved);
    }

    public void checkUserUserOwnerJobPostingValidation(UserOwnerJobPosting userOwnerJobPosting, UUID accountId) {
        if (!userOwnerJobPosting.getUser().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkOwnerUserOwnerJobPostingValidation(UserOwnerJobPosting userOwnerJobPosting, UUID accountId) {
        if (!userOwnerJobPosting.getOwner().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void saveFeedback(UserOwnerJobPosting userOwnerJobPosting, String feedback) {
        userOwnerJobPosting.updateFeedback(feedback);
    }

    private void updateResult(UserOwnerJobPosting userOwnerJobPosting, boolean isApproved){
        userOwnerJobPosting.updateResult(isApproved);
    }

}
