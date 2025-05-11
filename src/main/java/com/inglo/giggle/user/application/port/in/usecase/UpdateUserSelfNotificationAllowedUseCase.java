package com.inglo.giggle.user.application.port.in.usecase;

import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfNotificationAllowedCommand;

public interface UpdateUserSelfNotificationAllowedUseCase {

    /**
     * 알림 허용 변경하기 UseCase
     */
    void execute(UpdateUserSelfNotificationAllowedCommand command);
}
