package com.inglo.giggle.career.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUsersBookMarkCareerResponseDto extends SelfValidating<UpdateUsersBookMarkCareerResponseDto> {

    @NotNull(message = "is_book_marked는 null이 될 수 없습니다.")
    @JsonProperty("is_book_marked")
    private final Boolean isBookMarked;

    @Builder
    public UpdateUsersBookMarkCareerResponseDto(Boolean isBookMarked) {
        this.isBookMarked = isBookMarked;

        this.validateSelf();
    }

    public static UpdateUsersBookMarkCareerResponseDto of(Boolean isBookMarked) {
        return UpdateUsersBookMarkCareerResponseDto.builder()
                .isBookMarked(isBookMarked)
                .build();
    }
}
