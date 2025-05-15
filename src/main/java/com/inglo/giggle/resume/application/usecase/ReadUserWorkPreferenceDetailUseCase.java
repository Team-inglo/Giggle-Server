package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkPreferenceDetailResponseDto;

@UseCase
public interface ReadUserWorkPreferenceDetailUseCase {
    ReadUserWorkPreferenceDetailResponseDto execute(Long workPreferenceId);
}
