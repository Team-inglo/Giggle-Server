package com.inglo.giggle.notification.application.port.in.usecase;

import com.inglo.giggle.notification.application.port.in.command.HandlePushAlarmCommand;

public interface HandlePushAlarmUseCase {

    void execute(HandlePushAlarmCommand command);
}
