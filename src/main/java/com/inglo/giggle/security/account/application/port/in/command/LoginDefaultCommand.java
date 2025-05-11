package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.DefaultJsonWebTokenDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginDefaultCommand extends SelfValidating<LoginDefaultCommand> {

    @NotNull(message = "CustomUserPrincipal 은 null 일 수 없습니다.")
    private final CustomUserPrincipal principal;

    @NotNull(message = "DefaultJsonWebTokenDto 은 null 일 수 없습니다.")
    private final DefaultJsonWebTokenDto defaultJsonWebTokenDto;

    public LoginDefaultCommand(
            CustomUserPrincipal principal,
            DefaultJsonWebTokenDto defaultJsonWebTokenDto
    ) {
        this.principal = principal;
        this.defaultJsonWebTokenDto = defaultJsonWebTokenDto;
        this.validateSelf();
    }
}
