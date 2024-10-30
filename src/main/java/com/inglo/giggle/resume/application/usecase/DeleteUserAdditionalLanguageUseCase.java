package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface DeleteUserAdditionalLanguageUseCase {
    void execute(Long additionalLanguageId);
}
