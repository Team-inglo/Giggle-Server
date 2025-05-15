package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.UpdateUsersBookMarkCareerResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUsersBookMarkCareerUseCase {

    UpdateUsersBookMarkCareerResponseDto execute(
            UUID accountId,
            Long careerId
    );
}
