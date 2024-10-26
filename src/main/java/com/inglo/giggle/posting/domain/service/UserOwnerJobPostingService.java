package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOwnerJobPostingService {

    public Integer getSuccessFulHireCounts(List<UserOwnerJobPosting> userOwnerJobPostingList) {
        return Math.toIntExact(userOwnerJobPostingList.stream()
                .filter(userOwnerJobPosting -> userOwnerJobPosting.getStep().equals(EApplicationStep.APPLICATION_SUCCESS))
                .count());
    }

    public void updateStepFromResumeUnderReview(UserOwnerJobPosting userOwnerJobPosting, boolean isAccepted) {
        userOwnerJobPosting.updateStep(isAccepted ? EApplicationStep.WAITING_FOR_INTERVIEW : EApplicationStep.RESUME_REJECTED);
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
}
