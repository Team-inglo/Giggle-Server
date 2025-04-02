package com.inglo.giggle.posting.presentation.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JobPostingSearchId {

    private Long jobPostingId;

    private UUID ownerId;

    private List<Long> workDayTimeIds;

    public JobPostingSearchId(Long jobPostingId, UUID ownerId, List<Long> workDayTimeIds) {
        this.jobPostingId = jobPostingId;
        this.ownerId = ownerId;
        this.workDayTimeIds = workDayTimeIds;
    }

}