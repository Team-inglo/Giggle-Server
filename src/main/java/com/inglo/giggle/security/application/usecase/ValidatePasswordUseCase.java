package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.application.dto.request.ValidatePasswordRequestDto;
import com.inglo.giggle.security.application.dto.response.ValidatePasswordResponseDto;

import java.util.UUID;

@UseCase
public interface ValidatePasswordUseCase {
    ValidatePasswordResponseDto execute(UUID accountId, ValidatePasswordRequestDto requestDto);
}
