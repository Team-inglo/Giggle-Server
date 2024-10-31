package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateOwnerPartTimeEmploymentPermitUseCase {
    void execute(UUID accountId, Long documentId, UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto);
}
