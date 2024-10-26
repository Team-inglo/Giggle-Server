package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadOwnerDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadOwnerDetailUseCase {
    ReadOwnerDetailResponseDto execute(UUID accountId);
}
