package com.inglo.giggle.core.event.listener;

import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.event.dto.DeregisterDeviceTokenEventDto;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.event.dto.UpdateDeviceTokenEventDto;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.core.utility.JsonParseUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final RestClientUtil restClientUtil;

    @Value("${kakao.secret-key}")
    private String kakaoSecretKey;

    @Value("${push.notification.token-send-url}")
    private String pushNotificationTokenSendUrl;

    @Value("${push.notification.token-register-url}")
    private String pushNotificationTokenRegisterUrl;

    @Value("${push.notification.token-delete-url}")
    private String pushNotificationTokenDeregisterUrl;

    @Async
    @EventListener(classes = {NotificationEventDto.class})
    public void handleNotificationEvent(NotificationEventDto event) {

        // Kakao Authorization Header 생성
        Map<String, String> kakaoAuthorizationHeader = Map.of(
                Constants.AUTHORIZATION_HEADER, Constants.KAKAO_AUTHORIZATION_HEADER + kakaoSecretKey
        );
        HttpHeaders headers = HeaderUtil.createHeaders(kakaoAuthorizationHeader, MediaType.APPLICATION_FORM_URLENCODED);

        // `uuids` 목록 생성
        List<String> uuids = event.accountDevices().stream()
                .map(accountDevice -> accountDevice.getId().toString())
                .toList();

        // `push_message` 생성
        Map<String, Object> pushMessage = Map.of(
                "for_fcm", Map.of(
                        "notification", Map.of(
                                "title", event.title(),
                                "body", event.description()
                        )
                )
        );

        // JSON 변환
        String uuidsJson = JsonParseUtil.convertFromObjectToJson(uuids);
        String pushMessageJson = JsonParseUtil.convertFromObjectToJson(pushMessage);

        // FormData 구성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("uuids", uuidsJson);
        requestBody.add("push_message", pushMessageJson);

        restClientUtil.sendFormUrlEncodedPostMethod(pushNotificationTokenSendUrl, headers, requestBody);
    }

    @EventListener(classes = {UpdateDeviceTokenEventDto.class})
    public void handleRegisterDeviceTokenEvent(UpdateDeviceTokenEventDto event) {
        // Kakao Authorization Header 생성
        Map<String, String> kakaoAuthorizationHeader = Map.of(
                Constants.AUTHORIZATION_HEADER, Constants.KAKAO_AUTHORIZATION_HEADER + kakaoSecretKey
        );
        HttpHeaders headers = HeaderUtil.createHeaders(kakaoAuthorizationHeader, MediaType.APPLICATION_FORM_URLENCODED);

        // FormData 구성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("uuid", event.accountDevice().getId().toString());
        requestBody.add("device_id", event.accountDevice().getDeviceId().toString());
        requestBody.add("push_type", "fcm");
        String encodedPushToken = UriComponentsBuilder.fromPath("")
                .queryParam("push_token", event.accountDevice().getDeviceToken())
                .build()
                .encode()
                .toUriString()
                .replaceFirst("\\?", "");

        requestBody.add("push_token", encodedPushToken);

        restClientUtil.sendFormUrlEncodedPostMethod(pushNotificationTokenRegisterUrl, headers, requestBody);
    }

    @EventListener(classes = {DeregisterDeviceTokenEventDto.class})
    public void handleDeregisterDeviceTokenEvent(DeregisterDeviceTokenEventDto event) {
        // Kakao Authorization Header 생성
        Map<String, String> kakaoAuthorizationHeader = Map.of(
                Constants.AUTHORIZATION_HEADER, Constants.KAKAO_AUTHORIZATION_HEADER + kakaoSecretKey
        );
        HttpHeaders headers = HeaderUtil.createHeaders(kakaoAuthorizationHeader, MediaType.APPLICATION_FORM_URLENCODED);

        // FormData 구성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("uuid", event.accountDevice().getId().toString());
        requestBody.add("device_id", event.accountDevice().getDeviceId().toString());
        requestBody.add("push_type", "fcm");

        restClientUtil.sendFormUrlEncodedPostMethod(pushNotificationTokenDeregisterUrl, headers, requestBody);
    }
}
