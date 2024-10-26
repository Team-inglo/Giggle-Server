package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadOwnerBriefResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadOwnerBriefUseCase {
    ReadOwnerBriefResponseDto execute(UUID accountId);
}
