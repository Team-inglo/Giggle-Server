package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.request.UpdateUserLanguageRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserLanguageUseCase {
    void execute(UUID accountId, UpdateUserLanguageRequestDto requestDto);
}
