package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserPartTimeEmploymentPermitRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserPartTimeEmploymentPermitUseCase {
    void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserPartTimeEmploymentPermitRequestDto requestDto);
}
