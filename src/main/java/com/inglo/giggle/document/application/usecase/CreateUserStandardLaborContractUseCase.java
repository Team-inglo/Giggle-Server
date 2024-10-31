package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserStandardLaborContractRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserStandardLaborContractUseCase {
    void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserStandardLaborContractRequestDto requestDto);
}
