package com.inglo.giggle.notification.application.port.out;

import com.inglo.giggle.notification.domain.Notification;

public interface CreateNotificationPort {

    void createNotification(Notification notificationEntity);
}
