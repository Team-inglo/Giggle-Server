package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.CreateUserWorkPreferenceRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserWorkPreferenceUseCase {

    void execute(UUID accountId, CreateUserWorkPreferenceRequestDto requestDto);
}
