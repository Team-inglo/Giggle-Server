package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserPartTimeEmploymentPermitRequestDto;

@UseCase
public interface CreateUserPartTimeEmploymentPermitUseCase {
    void execute(Long userOwnerJobPostingId, CreateUserPartTimeEmploymentPermitRequestDto requestDto);
}
