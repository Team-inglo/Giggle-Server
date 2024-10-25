package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserOwnerJobPostingService {

    public Integer getSuccessFulHireCounts(List<UserOwnerJobPosting> userOwnerJobPostingList) {
        return Math.toIntExact(userOwnerJobPostingList.stream()
                .filter(userOwnerJobPosting -> userOwnerJobPosting.getStep().equals(EApplicationStep.APPLICATION_SUCCESS))
                .count());
    }

    public void validateUserOwnerJobPostingAndUser(UserOwnerJobPosting userOwnerJobPosting, UUID accountId) {
        if (!userOwnerJobPosting.getUser().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
    }

    public void validateUserOwnerJobPostingAndOwner(UserOwnerJobPosting userOwnerJobPosting, UUID accountId) {
        if (!userOwnerJobPosting.getOwner().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
    }
}
