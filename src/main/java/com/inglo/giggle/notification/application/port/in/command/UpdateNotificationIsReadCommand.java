package com.inglo.giggle.notification.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateNotificationIsReadCommand extends SelfValidating<UpdateNotificationIsReadCommand> {

    @NotNull(message = "notification_id 는 필수 값입니다.")
    private final Long notificationId;

    public UpdateNotificationIsReadCommand(Long notificationId) {
        this.notificationId = notificationId;
        this.validateSelf();
    }
}
