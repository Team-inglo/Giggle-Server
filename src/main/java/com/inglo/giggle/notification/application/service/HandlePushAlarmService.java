package com.inglo.giggle.notification.application.service;

import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.notification.application.port.in.command.HandlePushAlarmCommand;
import com.inglo.giggle.notification.application.port.in.usecase.HandlePushAlarmUseCase;
import com.inglo.giggle.notification.application.port.out.CreateNotificationPort;
import com.inglo.giggle.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlePushAlarmService implements HandlePushAlarmUseCase {

    private final CreateNotificationPort createNotificationPort;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void execute(HandlePushAlarmCommand command) {

        Notification notification = Notification.builder()
                .message(command.getEKafkaStatus().getMessage())
                .isRead(false)
                .notificationType(command.getENotificationType())
                .build();

        createNotificationPort.createNotification(notification);

        if (command.getNotificationAllowed() && !command.getDeviceTokens().isEmpty()) {
            applicationEventPublisher.publishEvent(
                    NotificationEventDto.of(
                            command.getTitle(),
                            notification.getMessage(),
                            command.getDeviceTokens()
                    )
            );
        }
    }
}
