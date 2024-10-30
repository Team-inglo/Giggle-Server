package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadOwnerDocumentSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerDocumentSummaryUseCase {
    ReadOwnerDocumentSummaryResponseDto readOwnerDocumentSummary(UUID accountId, Long userOwnerJobPostingId);
}
