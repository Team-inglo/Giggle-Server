package com.inglo.giggle.notification.application.service;

import com.inglo.giggle.notification.application.usecase.UpdateNotificationIsReadUseCase;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNotificationIsReadService implements UpdateNotificationIsReadUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long notificationId) {

        Notification notification = notificationRepository.findByIdOrElseThrow(notificationId);

        notification.updateIsRead();

        notificationRepository.save(notification);
    }
}
