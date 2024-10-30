package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserIntegratedApplicationRequestDto;

@UseCase
public interface CreateUserIntegratedApplicationUseCase {
    void execute(Long id, CreateUserIntegratedApplicationRequestDto requestDto);
}
