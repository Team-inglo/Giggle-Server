package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserWorkExperienceUseCase {
    void execute(UUID accountId, Long workExperienceId);
}
