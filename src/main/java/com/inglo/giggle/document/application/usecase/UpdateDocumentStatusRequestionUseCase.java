package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateDocumentStatusReqeustionRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateDocumentStatusRequestionUseCase {

    void updateDocumentStatusRequestion(UUID accountId, Long documentId, UpdateDocumentStatusReqeustionRequestDto requestDto);
}
