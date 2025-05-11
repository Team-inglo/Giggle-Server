package com.inglo.giggle.security.authenticationcodehistory.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteAuthenticationCodeHistoryCommand extends SelfValidating<DeleteAuthenticationCodeHistoryCommand> {

    @NotBlank(message = "email 은 null 이거나 공백일 수 없습니다.")
    private String email;

    public DeleteAuthenticationCodeHistoryCommand(String email) {
        this.email = email;

        validateSelf();
    }
}
