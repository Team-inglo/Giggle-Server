package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserEducationRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserEducationUseCase {
    void execute(UUID accountId, Long educationId, UpdateUserEducationRequestDto requestDto);
}
