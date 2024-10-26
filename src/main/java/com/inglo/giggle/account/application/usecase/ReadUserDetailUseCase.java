package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadUserDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadUserDetailUseCase {
    ReadUserDetailResponseDto execute(UUID accountId);
}
