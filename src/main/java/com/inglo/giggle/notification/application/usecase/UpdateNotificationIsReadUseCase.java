package com.inglo.giggle.notification.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateNotificationIsReadUseCase {

    /**
     * 알림 읽음 처리하기
     */
    void execute(
            UUID accountId,
            Long notificationId
    );
}
