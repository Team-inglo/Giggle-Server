package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateUserIntegratedApplicationRequestDto;

@UseCase
public interface UpdateUserIntegratedApplicationUseCase {
    void execute(Long documentId, UpdateUserIntegratedApplicationRequestDto requestDto);
}
