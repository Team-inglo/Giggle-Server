package com.inglo.giggle.core.event.listener;

import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final RestClientUtil restClientUtil;

    @Value("${kakao.secret-key}")
    private String kakaoSecretKey;

    @Value("${push.notification.token-send-url")
    private String pushNotificationTokenSendUrl;

    @Async
    @EventListener(classes = {NotificationEventDto.class})
    public void handleNotificationEvent(NotificationEventDto event) {
        Map<String, String> kakaoAuthorizationHeader = Map.of(
                Constants.KAKAO_AUTHORIZATION_HEADER, kakaoSecretKey
        );
        HttpHeaders headers = HeaderUtil.createHeaders(kakaoAuthorizationHeader);
        restClientUtil.sendFormUrlEncodedPostMethod()


    }
}
