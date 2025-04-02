package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserOwnerJobPostingService {

    /* -------------------------------------------- */
    /* Public Method ------------------------------ */
    /* -------------------------------------------- */
    public Integer getSuccessFulHireCounts(List<UserOwnerJobPosting> userOwnerJobPostings) {
        return Math.toIntExact(userOwnerJobPostings.stream()
                .filter(userOwnerJobPosting -> userOwnerJobPosting.getStep().equals(EApplicationStep.APPLICATION_SUCCESS))
                .count());
    }

    public void checkUserUserOwnerJobPostingValidation(UserOwnerJobPostingEntity userOwnerJobPostingEntity, UUID accountId) {
        if (!userOwnerJobPostingEntity.getUserEntity().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkOwnerUserOwnerJobPostingValidation(UserOwnerJobPostingEntity userOwnerJobPostingEntity, UUID accountId) {
        if (!userOwnerJobPostingEntity.getOwnerEntity().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

}
