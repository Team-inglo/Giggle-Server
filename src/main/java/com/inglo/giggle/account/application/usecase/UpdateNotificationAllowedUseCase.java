package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.presentation.dto.request.UpdateNotificationAllowedRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateNotificationAllowedUseCase {

    /**
     * 알림 허용 변경하기
     *
     * @param accountId 계정 ID
     * @param requestDto 알림 허용 변경하기
     */
    void execute(UUID accountId, UpdateNotificationAllowedRequestDto requestDto);
}
