package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserDocumentStatusSubmissionUseCase {
    void execute(UUID accountId, Long documentId);
}
