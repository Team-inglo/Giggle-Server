package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadIntegratedApplicationDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadIntegratedApplicationDetailUseCase {
    ReadIntegratedApplicationDetailResponseDto execute(UUID accountId, Long documentId);
}
