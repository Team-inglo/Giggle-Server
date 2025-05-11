package com.inglo.giggle.notification.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.notification.application.port.in.command.UpdateNotificationIsReadCommand;

@UseCase
public interface UpdateNotificationIsReadUseCase {

    /**
     * 알림 읽음 처리하기
     */
    void execute(UpdateNotificationIsReadCommand command);
}
