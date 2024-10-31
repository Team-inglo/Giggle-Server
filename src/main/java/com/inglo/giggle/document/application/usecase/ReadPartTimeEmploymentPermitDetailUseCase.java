package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadPartTimeEmploymentPermitDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadPartTimeEmploymentPermitDetailUseCase {
    ReadPartTimeEmploymentPermitDetailResponseDto execute(UUID accountId, Long documentId);
}
