package com.inglo.giggle.posting.application.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record JobPostingSearchIdRequestDto(
        List<JobPostingSearchId> jobPostingSearchIds
) {
}