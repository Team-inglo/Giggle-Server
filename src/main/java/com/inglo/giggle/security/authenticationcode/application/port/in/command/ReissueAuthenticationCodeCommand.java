package com.inglo.giggle.security.authenticationcode.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ReissueAuthenticationCodeCommand extends SelfValidating<ReissueAuthenticationCodeCommand> {

    @NotBlank(message = "이메일 정보가 존재하지 않습니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    public ReissueAuthenticationCodeCommand(String email) {
        this.email = email;
        this.validateSelf();
    }
}
