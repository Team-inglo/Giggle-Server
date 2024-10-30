package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerStandardLaborContractRequestDto;

@UseCase
public interface UpdateOwnerStandardLaborContractUseCase {
    void execute(Long documentId, UpdateOwnerStandardLaborContractRequestDto requestDto);
}
