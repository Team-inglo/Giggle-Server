package com.inglo.giggle.core.listener;

import com.inglo.giggle.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    private final static String TOPIC = "api.notification";

    @Async
    @EventListener(classes = {NotificationEvent.class})
    public void handleNotificationEvent(NotificationEvent event) {
//        log.info("NotificationEvent: {}", event);
//        kafkaTemplate.send(TOPIC, event.toPayload());
    }
}
