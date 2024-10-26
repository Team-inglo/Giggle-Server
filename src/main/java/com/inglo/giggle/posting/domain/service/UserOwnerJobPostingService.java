package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOwnerJobPostingService {

    /* -------------------------------------------- */
    /* Public Method ------------------------------ */
    /* -------------------------------------------- */

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

    /* -------------------------------------------- */
    /* Private Method ------------------------------ */
    /* -------------------------------------------- */
    private void saveFeedback(UserOwnerJobPosting userOwnerJobPosting, String feedback) {
        userOwnerJobPosting.updateFeedback(feedback);
    }

    private void updateResult(UserOwnerJobPosting userOwnerJobPosting, boolean isApproved){
        userOwnerJobPosting.updateResult(isApproved);
    }
}
