package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserResumeIsPublicResponseDto extends SelfValidating<UpdateUserResumeIsPublicResponseDto> {

    @JsonProperty("is_public")
    private final boolean isPublic;

    @Builder
    public UpdateUserResumeIsPublicResponseDto(
            boolean isPublic
    ) {
        this.isPublic = isPublic;
        this.validateSelf();
    }

    public static UpdateUserResumeIsPublicResponseDto of(
            boolean isPublic
    ) {
        return UpdateUserResumeIsPublicResponseDto.builder()
                .isPublic(isPublic)
                .build();
    }
}
