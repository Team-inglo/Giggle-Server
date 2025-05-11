package com.inglo.giggle.term.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTermDetailResult extends SelfValidating<ReadTermDetailResult> {
    
    @JsonProperty("content")
    private final String content;

    @Builder
    public ReadTermDetailResult(String content) {
        this.content = content;

        this.validateSelf();
    }

    public static ReadTermDetailResult of(String content) {
        return ReadTermDetailResult.builder()
                .content(content)
                .build();
    }
}
