package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserIntroductionRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserIntroductionUseCase {
    void execute(UUID accountId, UpdateUserIntroductionRequestDto requestDto);
}
