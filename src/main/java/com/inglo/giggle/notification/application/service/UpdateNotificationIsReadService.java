package com.inglo.giggle.notification.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.notification.application.usecase.UpdateNotificationIsReadUseCase;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNotificationIsReadService implements UpdateNotificationIsReadUseCase {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long notificationId) {

        Notification notification = notificationRepository.findByIdOrElseThrow(notificationId);

        notificationService.updateNotificationIsRead(notification);

        notificationRepository.save(notification);
    }
}
