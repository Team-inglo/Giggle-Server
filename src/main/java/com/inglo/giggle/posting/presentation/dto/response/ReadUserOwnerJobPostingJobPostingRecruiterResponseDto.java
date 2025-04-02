package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserOwnerJobPostingJobPostingRecruiterResponseDto extends SelfValidating<ReadUserOwnerJobPostingJobPostingRecruiterResponseDto> {

    @NotBlank(message = "recruiter_name은 null일 수 없습니다.")
    @JsonProperty("recruiter_name")
    private final String recruiterName;

    @NotBlank(message = "recruiter_phone_number는 null일 수 없습니다.")
    @JsonProperty("recruiter_phone_number")
    private final String recruiterPhoneNumber;

    @Builder
    public ReadUserOwnerJobPostingJobPostingRecruiterResponseDto(
            String recruiterName,
            String recruiterPhoneNumber
    ) {
        this.recruiterName = recruiterName;
        this.recruiterPhoneNumber = recruiterPhoneNumber;

        this.validateSelf();
    }

    public static ReadUserOwnerJobPostingJobPostingRecruiterResponseDto from(UserOwnerJobPosting userOwnerJobPosting) {
        return ReadUserOwnerJobPostingJobPostingRecruiterResponseDto.builder()
                .recruiterName(userOwnerJobPosting.getJobPostingInfo().getRecruiterName())
                .recruiterPhoneNumber(userOwnerJobPosting.getJobPostingInfo().getRecruiterPhoneNumber())
                .build();
    }
}