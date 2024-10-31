package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserIntegratedApplicationRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserIntegratedApplicationUseCase {
    void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserIntegratedApplicationRequestDto requestDto);
}
