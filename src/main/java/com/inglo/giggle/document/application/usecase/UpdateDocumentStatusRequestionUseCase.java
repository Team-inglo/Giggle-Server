package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateDocumentStatusReqeustionRequestDto;

@UseCase
public interface UpdateDocumentStatusRequestionUseCase {

    void updateDocumentStatusRequestion(Long documentId, UpdateDocumentStatusReqeustionRequestDto requestDto);
}
