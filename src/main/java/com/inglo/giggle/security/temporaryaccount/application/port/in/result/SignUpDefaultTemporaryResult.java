package com.inglo.giggle.security.temporaryaccount.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class SignUpDefaultTemporaryResult extends SelfValidating<SignUpDefaultTemporaryResult> {
    @JsonProperty(namespace = "try_cnt")
    @Min(0)
    private final Integer tryCnt;

    public SignUpDefaultTemporaryResult(Integer tryCnt) {
        this.tryCnt = tryCnt;

        validateSelf();
    }

    public static SignUpDefaultTemporaryResult of(Integer tryCnt) {
        return new SignUpDefaultTemporaryResult(
                tryCnt
        );
    }
}
