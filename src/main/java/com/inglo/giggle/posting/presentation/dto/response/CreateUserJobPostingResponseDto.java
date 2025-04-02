package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateUserJobPostingResponseDto extends SelfValidating<CreateUserJobPostingResponseDto> {

    @NotNull
    @JsonProperty("id")
    private final Long id;

    @Builder
    public CreateUserJobPostingResponseDto(Long id) {
        this.id = id;

        this.validateSelf();
    }

    public static CreateUserJobPostingResponseDto of(Long id) {
        return CreateUserJobPostingResponseDto.builder()
                .id(id)
                .build();
    }
}
