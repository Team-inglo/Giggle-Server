package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadUserSummaryResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadUserSummaryUseCase {
    ReadUserSummaryResponseDto execute(UUID accountId);
}
