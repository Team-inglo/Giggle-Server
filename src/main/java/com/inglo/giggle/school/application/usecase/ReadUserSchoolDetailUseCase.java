package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserSchoolDetailUseCase {
    ReadUserSchoolDetailResponseDto execute(UUID accountId);
}
