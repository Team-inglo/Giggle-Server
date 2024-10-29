package com.inglo.giggle.document.appliation.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ConfirmUserDocumentUseCase {
    void confirmUserDocument(UUID accountId, Long documentId);
}
