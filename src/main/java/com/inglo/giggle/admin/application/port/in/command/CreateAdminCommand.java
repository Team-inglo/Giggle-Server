package com.inglo.giggle.admin.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAdminCommand extends SelfValidating<CreateAdminCommand> {

    @NotNull(message = "id는 null일 수 없습니다.")
    private final UUID id;

    public CreateAdminCommand(UUID id) {
        this.id = id;
        this.validateSelf();
    }
}
