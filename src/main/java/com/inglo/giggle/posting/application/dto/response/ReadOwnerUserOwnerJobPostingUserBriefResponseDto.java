package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerUserOwnerJobPostingUserBriefResponseDto extends SelfValidating<ReadOwnerUserOwnerJobPostingUserBriefResponseDto> {

    @NotBlank(message = "applicant_name은 null일 수 없습니다.")
    @JsonProperty("applicant_name")
    private final String applicantName;

    @NotBlank(message = "applicant_phone_number는 null일 수 없습니다.")
    @JsonProperty("applicant_phone_number")
    private final String applicantPhoneNumber;

    @Builder
    public ReadOwnerUserOwnerJobPostingUserBriefResponseDto(
            String applicantName,
            String applicantPhoneNumber
    ) {
        this.applicantName = applicantName;
        this.applicantPhoneNumber = applicantPhoneNumber;

        this.validateSelf();
    }

    public static ReadOwnerUserOwnerJobPostingUserBriefResponseDto fromEntity(User user) {
        return ReadOwnerUserOwnerJobPostingUserBriefResponseDto.builder()
                .applicantName(user.getName())
                .applicantPhoneNumber(user.getPhoneNumber())
                .build();
    }
}
