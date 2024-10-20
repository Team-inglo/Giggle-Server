package com.inglo.giggle.security.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.security.domain.mysql.Account;
import lombok.Builder;

@Builder
public record AccountBriefInfoResponseDto(
        @JsonProperty("account_type")
        String accountType,

        @JsonProperty("name")
        String name
) {
    public static AccountBriefInfoResponseDto of(Account account) {
            return AccountBriefInfoResponseDto.builder()
                    .accountType(account.getRole().getEnName())
                    .name(account.getName())
                    .build();
    }
}
