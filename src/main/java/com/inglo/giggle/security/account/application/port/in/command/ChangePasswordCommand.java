package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangePasswordCommand extends SelfValidating<ChangePasswordCommand> {

    @NotNull(message = "accountId 은 null일 수 없습니다.")
    private UUID accountId;

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private final String currentPassword;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*?])[a-zA-Z0-9~!@#$%^&*?]{8,20}$",
            message = "비밀번호는 8글자 이상 20자 이하로, 영어 대소문자, 숫자, 특수문자 중 3가지를 적어도 하나 이상씩 사용하고, 조합하여 입력해주세요.")
    private final String newPassword;

    public ChangePasswordCommand(
            UUID accountId,
            String currentPassword,
            String newPassword
    ) {
        this.accountId = accountId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;

        this.validateSelf();
    }
}
