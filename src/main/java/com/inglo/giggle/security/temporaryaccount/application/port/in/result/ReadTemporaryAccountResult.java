package com.inglo.giggle.security.temporaryaccount.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReadTemporaryAccountResult extends SelfValidating<ReadTemporaryAccountResult> {

    @JsonProperty("email")
    @NotNull(message = "이메일은 필수입니다.")
    private final String email;

    @JsonProperty("password")
    @NotNull(message = "비밀번호는 필수입니다.")
    private final String password;

    @JsonProperty("account_type")
    @NotNull(message = "계정 유형은 필수입니다.")
    private final ESecurityRole accountType;

    public ReadTemporaryAccountResult(
            String email,
            String password,
            ESecurityRole accountType
    ) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.validateSelf();
    }

    public static ReadTemporaryAccountResult of(String email, String password, ESecurityRole role) {
        return new ReadTemporaryAccountResult(
                email,
                password,
                role
        );
    }
}
