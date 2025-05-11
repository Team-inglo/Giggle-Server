package com.inglo.giggle.owner.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateOwnerNotificationAllowedCommand extends SelfValidating<UpdateOwnerNotificationAllowedCommand> {

    @NotNull(message = "accountId는 null일 수 없습니다.")
    private final UUID accountId;

    @NotNull(message = "is_notification_allowed는 null일 수 없습니다.")
    private final Boolean isNotificationAllowed;

    public UpdateOwnerNotificationAllowedCommand(UUID accountId, Boolean isNotificationAllowed) {
        this.accountId = accountId;
        this.isNotificationAllowed = isNotificationAllowed;
        this.validateSelf();
    }
}
