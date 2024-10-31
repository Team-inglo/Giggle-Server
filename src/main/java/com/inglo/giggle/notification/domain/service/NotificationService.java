package com.inglo.giggle.notification.domain.service;

import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public Notification createNotification(
            String message,
            UserOwnerJobPosting userOwnerJobPosting,
            ENotificationType notificationType
    ) {
        return Notification.builder()
                .userOwnerJobPosting(userOwnerJobPosting)
                .message(message)
                .notificationType(notificationType)
                .build();
    }
}
