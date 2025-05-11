package com.inglo.giggle.notification.application.service;

import com.inglo.giggle.notification.application.port.in.command.UpdateNotificationIsReadCommand;
import com.inglo.giggle.notification.application.port.in.usecase.UpdateNotificationIsReadUseCase;
import com.inglo.giggle.notification.application.port.out.LoadNotificationPort;
import com.inglo.giggle.notification.application.port.out.UpdateNotificationPort;
import com.inglo.giggle.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateNotificationIsReadService implements UpdateNotificationIsReadUseCase {

    private final LoadNotificationPort loadNotificationPort;
    private final UpdateNotificationPort updateNotificationPort;

    @Override
    @Transactional
    public void execute(UpdateNotificationIsReadCommand command) {

        Notification notification = loadNotificationPort.loadNotification(command.getNotificationId());

        notification.updateIsRead();

        updateNotificationPort.updateNotification(notification);
    }
}
