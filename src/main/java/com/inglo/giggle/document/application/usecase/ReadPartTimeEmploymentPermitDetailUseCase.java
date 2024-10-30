package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadPartTimeEmploymentPermitDetailResponseDto;

@UseCase
public interface ReadPartTimeEmploymentPermitDetailUseCase {
    ReadPartTimeEmploymentPermitDetailResponseDto execute(Long documentId);
}
