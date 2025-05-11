package com.inglo.giggle.security.temporaryaccount.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteTemporaryAccountCommand extends SelfValidating<DeleteTemporaryAccountCommand> {

    @NotBlank(message = "이메일은 필수입니다.")
    private final String email;

    public DeleteTemporaryAccountCommand(
            String email
    ) {
        this.email = email;
        this.validateSelf();
    }
}
