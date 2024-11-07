package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateOwnerDocumentStatusSubmissionUseCase {

    /**
     * (고용주) 서류 제출하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     */
    void execute(UUID accountId, Long documentId);
}
