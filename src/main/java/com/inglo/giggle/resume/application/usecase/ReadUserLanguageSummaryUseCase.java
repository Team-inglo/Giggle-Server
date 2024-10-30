package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserLanguageSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserLanguageSummaryUseCase {
    ReadUserLanguageSummaryResponseDto execute(UUID accountId);
}
