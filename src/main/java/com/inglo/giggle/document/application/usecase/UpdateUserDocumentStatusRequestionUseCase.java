package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateDocumentStatusReqeustionRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserDocumentStatusRequestionUseCase {

    /**
     * 서류 재검토 요청하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     * @param requestDto 서류 재검토 요청 DTO
     */
    void execute(UUID accountId, Long documentId, UpdateDocumentStatusReqeustionRequestDto requestDto);
}
