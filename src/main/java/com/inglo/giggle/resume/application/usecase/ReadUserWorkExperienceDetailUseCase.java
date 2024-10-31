package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkExperienceDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserWorkExperienceDetailUseCase {
    ReadUserWorkExperienceDetailResponseDto execute(UUID accountId, Long workExperienceId);
}
