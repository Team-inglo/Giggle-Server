package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserWorkPreferenceUseCase {
    void execute(UUID accountId, Long workPreferenceId);
}
