package com.inglo.giggle.notification.application.port.out;

import com.inglo.giggle.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadNotificationPort {

    Notification loadNotification(Long id);

    Page<Notification> loadNotificationByUserId(UUID userId, Pageable pageable);

    Page<Notification> loadNotificationByOwnerId(UUID ownerId, Pageable pageable);
}
