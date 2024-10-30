package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserStandardLaborContractRequestDto;

@UseCase
public interface CreateUserStandardLaborContractUseCase {
    void execute(Long id, CreateUserStandardLaborContractRequestDto requestDto);
}
