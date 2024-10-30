package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.CreateUserWorkExperienceRequestDto;

@UseCase
public interface CreateUserWorkExperienceUseCase {
    void execute(CreateUserWorkExperienceRequestDto requestDto);
}
