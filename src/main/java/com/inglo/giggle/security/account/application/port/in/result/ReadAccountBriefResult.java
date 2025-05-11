package com.inglo.giggle.security.account.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAccountBriefResult extends SelfValidating<ReadAccountBriefResult> {

    @JsonProperty("account_type")
    @NotNull
    private final String accountType;

    @JsonProperty("name")
    @NotNull
    private final String name;

    @Builder
    public ReadAccountBriefResult(
            String accountType,
            String name
    ) {
        this.accountType = accountType;
        this.name = name;
        this.validateSelf();
    }

    public static ReadAccountBriefResult of(ESecurityRole role, String name) {
        return ReadAccountBriefResult.builder()
                .accountType(role.getEnName())
                .name(name)
                .build();
    }
}
