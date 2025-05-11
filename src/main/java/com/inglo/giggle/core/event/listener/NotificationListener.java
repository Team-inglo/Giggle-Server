package com.inglo.giggle.core.event.listener;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    @Async
    @EventListener(classes = {NotificationEventDto.class})
    public void handleNotificationEvent(NotificationEventDto event) {
        event.deviceTokens()
                .forEach( deviceToken -> {
                    try {
                        // FCM 메시지 생성 및 전송
                        Message data = Message.builder()
                                .setToken(deviceToken)
                                .putData("time", LocalDateTime.now().toString())
                                .setNotification(
                                        new Notification(
                                                event.title(),
                                                event.description()
                                        )
                                )
                                .build();

                        // FCM 메시지 전송
                        FirebaseMessaging.getInstance().send(data);
                    } catch (Exception e) {
                        log.error("FCM 메시지 전송에 실패하였습니다. {}", e.getMessage());
                    }
                });
    }
}
