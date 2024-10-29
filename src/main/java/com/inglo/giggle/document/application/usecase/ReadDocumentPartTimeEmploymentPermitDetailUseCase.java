package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadDocumentPartTimeEmploymentPermitDetailResponseDto;

@UseCase
public interface ReadDocumentPartTimeEmploymentPermitDetailUseCase {
    ReadDocumentPartTimeEmploymentPermitDetailResponseDto execute(Long documentId);
}
