package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserEducationRequestDto;

@UseCase
public interface UpdateUserEducationUseCase {
    void execute(Long educationId, UpdateUserEducationRequestDto requestDto);
}
