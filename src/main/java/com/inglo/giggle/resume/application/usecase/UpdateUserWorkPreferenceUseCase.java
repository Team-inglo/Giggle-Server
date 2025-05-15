package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkPreferenceRequestDto;

@UseCase
public interface UpdateUserWorkPreferenceUseCase {

    void execute(Long workPreferenceId, UpdateUserWorkPreferenceRequestDto requestDto);
}
