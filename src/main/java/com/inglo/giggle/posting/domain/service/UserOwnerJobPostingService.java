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
}
