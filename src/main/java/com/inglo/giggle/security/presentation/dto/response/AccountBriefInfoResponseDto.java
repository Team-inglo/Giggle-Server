package com.inglo.giggle.security.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountBriefInfoResponseDto extends SelfValidating<AccountBriefInfoResponseDto> {

    @JsonProperty("account_type")
    @NotNull
    private final String accountType;

    @JsonProperty("name")
    @NotNull
    private final String name;

    @Builder
    public AccountBriefInfoResponseDto(
            String accountType,
            String name
    ) {
        this.accountType = accountType;
        this.name = name;
        this.validateSelf();
    }

    public static AccountBriefInfoResponseDto of(Account account) {
        return AccountBriefInfoResponseDto.builder()
                .accountType(account.getRole().getEnName())
                .name(account.getName())
                .build();
    }
}
