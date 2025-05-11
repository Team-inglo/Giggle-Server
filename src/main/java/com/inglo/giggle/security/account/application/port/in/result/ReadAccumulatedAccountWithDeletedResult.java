package com.inglo.giggle.security.account.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class ReadAccumulatedAccountWithDeletedResult extends SelfValidating<ReadAccumulatedAccountWithDeletedResult> {

    @JsonProperty("accountIds")
    @NotEmpty(message = "accountIds must not be empty")
    private final List<AccountDto> accountDtos;

    public ReadAccumulatedAccountWithDeletedResult(List<AccountDto> accountDtos) {
        this.accountDtos = accountDtos;

        this.validateSelf();
    }

    @Getter
    public static class AccountDto extends SelfValidating<AccountDto> {
        @JsonProperty("id")
        @NotNull(message = "accountId must not be null")
        private UUID accountId;

        @JsonProperty("role")
        @NotNull(message = "role must not be null")
        private ESecurityRole role;

        @JsonProperty("create_at")
        @NotNull(message = "createAt must not be null")
        private LocalDateTime createdAt;

        @JsonProperty("deleted_at")
        private LocalDateTime deletedAt;

        public AccountDto(UUID accountId, ESecurityRole role, LocalDateTime createdAt, LocalDateTime deletedAt) {
            this.accountId = accountId;
            this.role = role;
            this.createdAt = createdAt;
            this.deletedAt = deletedAt;

            this.validateSelf();
        }
    }

    public static ReadAccumulatedAccountWithDeletedResult of(List<AccountDto> accountDtos) {
        return new ReadAccumulatedAccountWithDeletedResult(accountDtos);
    }
}
