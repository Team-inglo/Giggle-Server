package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkExperienceRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserWorkExperienceUseCase {
    void execute(UUID accountId, Long workExperienceId, UpdateUserWorkExperienceRequestDto requestDto);
}
