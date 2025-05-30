package com.inglo.giggle.resume.application.dto.response;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateOwnerResumeBookMarkResumeResponseDto extends SelfValidating<UpdateOwnerResumeBookMarkResumeResponseDto> {

    @NotNull(message = "is_book_marked는 null이 될 수 없습니다.")
    private final Boolean isBookMarked;

    @Builder
    public UpdateOwnerResumeBookMarkResumeResponseDto(Boolean isBookMarked) {
        this.isBookMarked = isBookMarked;

        this.validateSelf();
    }

    public static UpdateOwnerResumeBookMarkResumeResponseDto of(Boolean isBookMarked) {
        return UpdateOwnerResumeBookMarkResumeResponseDto.builder()
                .isBookMarked(isBookMarked)
                .build();
    }
}
