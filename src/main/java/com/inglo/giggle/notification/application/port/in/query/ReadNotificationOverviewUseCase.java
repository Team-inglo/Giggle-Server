package com.inglo.giggle.notification.application.port.in.query;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.notification.application.port.in.result.ReadNotificationOverviewResponseDto;

import java.util.UUID;

@UseCase
public interface ReadNotificationOverviewUseCase {
    /**
     * 알림 개요 조회하기
     */
    ReadNotificationOverviewResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size
    );
}
