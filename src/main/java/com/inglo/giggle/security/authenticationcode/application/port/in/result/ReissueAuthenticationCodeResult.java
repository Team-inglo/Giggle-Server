package com.inglo.giggle.security.authenticationcode.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueAuthenticationCodeResult extends SelfValidating<ReissueAuthenticationCodeResult> {
    @JsonProperty(namespace = "try_cnt")
    @Min(0)
    private final Integer tryCnt;

    @Builder
    public ReissueAuthenticationCodeResult(Integer tryCnt) {
        this.tryCnt = tryCnt;

        validateSelf();
    }

    public static ReissueAuthenticationCodeResult fromEntity(Integer tryCnt) {
        return ReissueAuthenticationCodeResult.builder()
                .tryCnt(tryCnt)
                .build();
    }
}
