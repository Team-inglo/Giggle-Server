package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerStandardLaborContractRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateOwnerStandardLaborContractUseCase {
    void execute(UUID accountId, Long documentId, UpdateOwnerStandardLaborContractRequestDto requestDto);
}
