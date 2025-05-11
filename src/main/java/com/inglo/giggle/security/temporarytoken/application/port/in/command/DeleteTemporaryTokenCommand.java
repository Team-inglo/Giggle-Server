package com.inglo.giggle.security.temporarytoken.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteTemporaryTokenCommand extends SelfValidating<DeleteTemporaryTokenCommand> {

    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;

    public DeleteTemporaryTokenCommand(String email) {
        this.email = email;
        this.validateSelf();
    }
}
