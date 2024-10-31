package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserAdditionalLanguageUseCase {
    void execute(UUID accountId, Long additionalLanguageId);
}
