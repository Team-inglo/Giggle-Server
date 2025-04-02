package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserJobPostingBookMarkResponseDto extends SelfValidating<UpdateUserJobPostingBookMarkResponseDto> {

    @NotNull(message = "is_book_marked는 null이 될 수 없습니다.")
    @JsonProperty("is_book_marked")
    private final Boolean isBookMarked;

    @Builder
    public UpdateUserJobPostingBookMarkResponseDto(Boolean isBookMarked) {
        this.isBookMarked = isBookMarked;

        this.validateSelf();
    }

    public static UpdateUserJobPostingBookMarkResponseDto of(Boolean isBookMarked) {
        return UpdateUserJobPostingBookMarkResponseDto.builder()
                .isBookMarked(isBookMarked)
                .build();
    }
}
