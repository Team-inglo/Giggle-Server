package com.inglo.giggle.term.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.term.domain.Term;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTermDetailResponseDto extends SelfValidating<ReadTermDetailResponseDto> {
    
    @JsonProperty("content")
    private final String content;

    @Builder
    public ReadTermDetailResponseDto(String content) {
        this.content = content;

        this.validateSelf();
    }

    public static ReadTermDetailResponseDto fromEntity(Term term) {
        return ReadTermDetailResponseDto.builder()
                .content(term.getContent())
                .build();
    }
}
