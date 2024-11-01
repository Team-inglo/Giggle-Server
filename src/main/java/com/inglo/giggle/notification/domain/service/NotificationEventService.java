package com.inglo.giggle.notification.domain.service;

import com.inglo.giggle.notification.event.NotificationEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventService {

    public NotificationEvent createNotificationEvent(String title, String description, String deviceToken) {
        return NotificationEvent.builder()
                .title(title)
                .description(description)
                .deviceToken(deviceToken)
                .build();
    }
}
