package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateUserIntegratedApplicationRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserIntegratedApplicationUseCase {
    void execute(UUID accountId, Long documentId, UpdateUserIntegratedApplicationRequestDto requestDto);
}
