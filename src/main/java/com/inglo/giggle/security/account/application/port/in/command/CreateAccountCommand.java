package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateAccountCommand extends SelfValidating<CreateAccountCommand> {

    @NotNull(message = "provider 는 null일 수 없습니다.")
    private final ESecurityProvider provider;

    @NotNull(message = "role 는 null일 수 없습니다.")
    private final ESecurityRole role;

    @NotNull(message = "serialId 는 null일 수 없습니다.")
    private final String serialId;

    @NotNull(message = "password 는 null일 수 없습니다.")
    private final String password;

    public CreateAccountCommand(
            ESecurityProvider provider,
            ESecurityRole role,
            String serialId,
            String password
    ) {
        this.provider = provider;
        this.role = role;
        this.serialId = serialId;
        this.password = password;

        this.validateSelf();
    }
}
