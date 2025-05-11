package com.inglo.giggle.security.account.application.port.in.result;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReadAccountRoleResult extends SelfValidating<ReadAccountRoleResult> {

    @NotNull(message = "role 은 null일 수 없습니다.")
    private final ESecurityRole role;

    public ReadAccountRoleResult(ESecurityRole role) {
        this.role = role;

        this.validateSelf();
    }
}
