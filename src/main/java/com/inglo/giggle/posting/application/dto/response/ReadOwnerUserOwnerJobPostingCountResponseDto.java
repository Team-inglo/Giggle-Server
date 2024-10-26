package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerUserOwnerJobPostingCountResponseDto extends SelfValidating<ReadOwnerUserOwnerJobPostingCountResponseDto> {

    @NotNull(message = "job_postings_counts는 null일 수 없습니다.")
    @JsonProperty("job_postings_counts")
    private final Integer jobPostingsCounts;

    @NotNull(message = "applicants_counts는 null일 수 없습니다.")
    @JsonProperty("applicants_counts")
    private final Integer applicantsCounts;

    @NotNull(message = "successful_hire_counts는 null일 수 없습니다.")
    @JsonProperty("successful_hire_counts")
    private final Integer successfulHireCounts;

    @Builder
    public ReadOwnerUserOwnerJobPostingCountResponseDto(
            Integer jobPostingsCounts,
            Integer applicantsCounts,
            Integer successfulHireCounts
    ) {
        this.jobPostingsCounts = jobPostingsCounts;
        this.applicantsCounts = applicantsCounts;
        this.successfulHireCounts = successfulHireCounts;

        this.validateSelf();
    }

    public static ReadOwnerUserOwnerJobPostingCountResponseDto of(
            Integer jobPostingsCounts,
            Integer applicantsCounts,
            Integer successfulHireCounts
    ) {

        return ReadOwnerUserOwnerJobPostingCountResponseDto.builder()
                .jobPostingsCounts(jobPostingsCounts)
                .applicantsCounts(applicantsCounts)
                .successfulHireCounts(successfulHireCounts)
                .build();
    }
}
