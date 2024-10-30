package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;

@UseCase
public interface UpdateOwnerPartTimeEmploymentPermitUseCase {
    void execute(Long documentId, UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto);
}
