package com.inglo.giggle.security.authenticationcode.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ValidateAuthenticationCodeCommand extends SelfValidating<ValidateAuthenticationCodeCommand> {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "잘못된 이메일 형식입니다.")
    private final String email;

    @NotBlank(message = "인증 코드를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{6}$", message = "인증 코드는 6자리의 숫자로 이루어져 있습니다.")
    private final String authenticationCode;

    public ValidateAuthenticationCodeCommand(
            String email,
            String authenticationCode
    ) {
        this.email = email;
        this.authenticationCode = authenticationCode;

        this.validateSelf();
    }
}
