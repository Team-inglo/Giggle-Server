package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadUserDocumentSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserDocumentSummaryUseCase {
    ReadUserDocumentSummaryResponseDto readUserDocumentSummary(UUID accountId, Long userOwnerJobPostingId);
}
