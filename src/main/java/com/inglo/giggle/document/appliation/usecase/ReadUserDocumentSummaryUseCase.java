package com.inglo.giggle.document.appliation.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.appliation.dto.response.ReadUserDocumentSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserDocumentSummaryUseCase {
    ReadUserDocumentSummaryResponseDto readUserDocumentSummary(UUID accountId, Long userOwnerJobPostingId);
}
