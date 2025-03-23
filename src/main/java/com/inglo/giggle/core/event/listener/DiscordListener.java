package com.inglo.giggle.core.event.listener;

import com.inglo.giggle.core.event.dto.SendDiscordEventDto;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.core.utility.JsonParseUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordListener {

    @Value("${discord.webhook-url}")
    private String discordWebhookUrl;

    private final RestClientUtil restClientUtil;

    @Async
    @EventListener
    public void sendDiscordMessage(SendDiscordEventDto event) {
        String stackTrace = Arrays.stream(event.e().getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

        String rawMessage = """
                 ⚠️ *예외 발생*
                 ▪️ 예외 타입: %s
                 ▪️ 메시지: %s
                 ▪️ 발생 위치:\s
                 %s
                \s""".formatted(
                event.e().getClass().getSimpleName(),
                event.e().getMessage(),
                stackTrace
        );

        // 1999자 초과 시 자르기
        String message = rawMessage.length() > 1999
                ? rawMessage.substring(0, 1980) + "\n... (생략됨)"
                : rawMessage;

        Map<String, String> content = Map.of(
                "content", message
        );

        restClientUtil.sendNonReturnPostMethod(
                discordWebhookUrl,
                HeaderUtil.createJsonHeader(),
                JsonParseUtil.convertFromObjectToJson(content)
        );
    }

}
