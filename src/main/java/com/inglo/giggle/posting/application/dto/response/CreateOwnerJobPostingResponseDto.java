package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateOwnerJobPostingResponseDto extends SelfValidating<CreateOwnerJobPostingResponseDto> {

    @NotNull
    @JsonProperty("id")
    private final Long id;

    @Builder
    public CreateOwnerJobPostingResponseDto(Long id) {
        this.id = id;

        this.validateSelf();
    }
}
