package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateUserStandardLaborContractRequestDto;

@UseCase
public interface UpdateUserStandardLaborContractUseCase {
    void execute(Long documentId, UpdateUserStandardLaborContractRequestDto requestDto);
}
