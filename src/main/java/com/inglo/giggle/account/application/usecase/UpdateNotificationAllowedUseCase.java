package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.request.UpdateNotificationAllowedRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateNotificationAllowedUseCase {
    void execute(UUID accountId, UpdateNotificationAllowedRequestDto requestDto);
}
