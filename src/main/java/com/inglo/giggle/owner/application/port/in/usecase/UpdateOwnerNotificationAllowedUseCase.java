package com.inglo.giggle.owner.application.port.in.usecase;

import com.inglo.giggle.owner.application.port.in.command.UpdateOwnerNotificationAllowedCommand;

public interface UpdateOwnerNotificationAllowedUseCase {

    /**
     * 알림 허용 변경하기 UseCase
     */
    void execute(UpdateOwnerNotificationAllowedCommand command);
}
