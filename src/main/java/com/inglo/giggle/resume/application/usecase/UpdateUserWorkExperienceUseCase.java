package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkExperienceRequestDto;

@UseCase
public interface UpdateUserWorkExperienceUseCase {
    void execute(Long workExperienceId, UpdateUserWorkExperienceRequestDto requestDto);
}
