package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadIntegratedApplicationDetailResponseDto;

@UseCase
public interface ReadIntegratedApplicationDetailUseCase {
    ReadIntegratedApplicationDetailResponseDto execute(Long documentId);
}
