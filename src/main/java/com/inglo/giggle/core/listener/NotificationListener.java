package com.inglo.giggle.core.listener;

import com.inglo.giggle.core.utility.JsonParseUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    @Value("${server.notification-server}")
    private String notificationServer;

    private final RestClientUtil restClientUtil;

    @Async
    @EventListener(classes = {NotificationEvent.class})
    public void handleNotificationEvent(NotificationEvent event) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        restClientUtil.sendPostMethod(
                notificationServer,
                httpHeaders,
                JsonParseUtil.convertFromObjectToJson(event.toPayload())
        );
    }
}
