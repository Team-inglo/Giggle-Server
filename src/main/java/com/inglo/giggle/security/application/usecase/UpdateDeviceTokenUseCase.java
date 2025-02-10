package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.application.dto.request.UpdateDeviceTokenRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateDeviceTokenUseCase {

    void execute(
            UUID accountId,
            UpdateDeviceTokenRequestDto requestDto
    );
}
