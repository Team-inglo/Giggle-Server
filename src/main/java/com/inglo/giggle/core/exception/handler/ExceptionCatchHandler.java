package com.inglo.giggle.core.exception.handler;

import com.inglo.giggle.core.event.dto.SendDiscordEventDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionCatchHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            applicationEventPublisher.publishEvent(
                    SendDiscordEventDto.of(
                            throwable
                    )
            );
        });
    }
}
