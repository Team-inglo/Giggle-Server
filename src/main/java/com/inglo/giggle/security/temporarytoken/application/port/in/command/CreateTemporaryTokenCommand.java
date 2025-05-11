package com.inglo.giggle.security.temporarytoken.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateTemporaryTokenCommand extends SelfValidating<CreateTemporaryTokenCommand> {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotNull
    private final String temporaryToken;

    public CreateTemporaryTokenCommand(
            String email,
            String temporaryToken
    ) {
        this.email = email;
        this.temporaryToken = temporaryToken;

        this.validateSelf();
    }
}
