package com.inglo.giggle.security.authenticationcodehistory.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReadAuthenticationCodeHistoryResult extends SelfValidating<ReadAuthenticationCodeHistoryResult> {

    @JsonProperty("count")
    @Min(0)
    private Integer count;

    @JsonProperty("last_sent_at")
    @NotNull(message = "lastSentAt 은 null 이거나 공백일 수 없습니다.")
    private LocalDateTime lastSentAt;

    public ReadAuthenticationCodeHistoryResult(
            Integer count,
            LocalDateTime lastSentAt
    ) {
        this.count = count;
        this.lastSentAt = lastSentAt;
    }

    public static ReadAuthenticationCodeHistoryResult of(
            Integer count,
            LocalDateTime lastSentAt
    ) {

        return new ReadAuthenticationCodeHistoryResult(
                count,
                lastSentAt
        );
    }
}
