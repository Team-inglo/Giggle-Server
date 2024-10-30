package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.CreateUserEducationRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserEducationUseCase {
    void execute(UUID accountId, CreateUserEducationRequestDto requestDto);
}
