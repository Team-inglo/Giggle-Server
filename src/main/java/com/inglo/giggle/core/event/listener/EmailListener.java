package com.inglo.giggle.core.event.listener;

import com.inglo.giggle.core.utility.MailUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.security.event.ChangePasswordBySystemEvent;
import com.inglo.giggle.security.event.CompleteEmailValidationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailListener {

    private final MailUtil mailUtil;
    private final RestClientUtil restClientUtil;

    @Value("${spring.mail.host}")
    private String requestUrl;

    @Async
    @EventListener(classes = {CompleteEmailValidationEvent.class})
    public void handleCompleteEmailValidationEvent(CompleteEmailValidationEvent event) {
        log.info(
                "\n----------------------------------\n[ 이메일 인증 완료 이벤트 처리 ]\n{}\n{}\n----------------------------------",
                event.receiverAddress() + "님의 이메일 인증이 완료되었습니다.",
                "인증코드는 " + event.authenticationCode() + " 입니다."
        );

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = mailUtil.getSendAuthenticationCodeRequestBody(
                    event.receiverAddress(),
                    event.authenticationCode()
            );

            restClientUtil.sendPostMethod(requestUrl, headers, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @EventListener(classes = {ChangePasswordBySystemEvent.class})
    public void handleChangePasswordBySystemEvent(ChangePasswordBySystemEvent event) {
        log.info(
                "\n----------------------------------\n[ 임시 비밀번호 발급 이벤트 처리 ]\n{}\n{}\n----------------------------------",
                event.receiverAddress() + "님의 임시 비밀번호가 발급되었습니다.",
                "임시 비밀번호는 " + event.temporaryPassword() + " 입니다."
        );

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = mailUtil.getSendTemporaryPasswordRequestBody(
                    event.receiverAddress(),
                    event.temporaryPassword()
            );

            restClientUtil.sendPostMethod(requestUrl, headers, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
