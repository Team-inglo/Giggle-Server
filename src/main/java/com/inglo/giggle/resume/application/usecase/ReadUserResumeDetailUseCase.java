package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserResumeDetailUseCase {
    ReadUserResumeDetailResponseDto execute(UUID accountId);
}
