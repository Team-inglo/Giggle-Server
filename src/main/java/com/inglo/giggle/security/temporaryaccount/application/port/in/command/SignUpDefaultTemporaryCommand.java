package com.inglo.giggle.security.temporaryaccount.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpDefaultTemporaryCommand extends SelfValidating<SignUpDefaultTemporaryCommand> {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*?])[a-zA-Z0-9~!@#$%^&*?]{8,20}$",
            message = "비밀번호는 8글자 이상 20자 이하로, 영어 대소문자, 숫자, 특수문자(~!@#$%^&*?) 중 3가지를 적어도 하나 이상씩 사용하고, 조합하여 입력해주세요.")
    private final String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotBlank(message = "계정 타입을 입력해주세요.")
    private final String accountType;

    public SignUpDefaultTemporaryCommand(
            String password,
            String email,
            String accountType
    ) {
        this.password = password;
        this.email = email;
        this.accountType = accountType;

        this.validateSelf();
    }
}
