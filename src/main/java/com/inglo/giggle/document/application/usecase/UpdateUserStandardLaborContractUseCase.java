package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateUserStandardLaborContractRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserStandardLaborContractUseCase {
    void execute(UUID accountId, Long documentId, UpdateUserStandardLaborContractRequestDto requestDto);
}
