package com.inglo.giggle.notification.application.port.out;

import com.inglo.giggle.notification.domain.Notification;

public interface UpdateNotificationPort {

    void updateNotification(Notification notificationEntity);
}
