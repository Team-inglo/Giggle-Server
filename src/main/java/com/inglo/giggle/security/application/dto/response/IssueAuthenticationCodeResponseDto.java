package com.inglo.giggle.security.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueAuthenticationCodeResponseDto extends SelfValidating<IssueAuthenticationCodeResponseDto> {
    @JsonProperty(namespace = "try_cnt")
    @Min(0)
    private final Integer tryCnt;

    @Builder
    public IssueAuthenticationCodeResponseDto(Integer tryCnt) {
        this.tryCnt = tryCnt;

        validateSelf();
    }

    public static IssueAuthenticationCodeResponseDto fromEntity(AuthenticationCodeHistory entity) {
        return IssueAuthenticationCodeResponseDto.builder()
                .tryCnt(entity.getCount())
                .build();
    }
}
