package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeCompletionRateResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserResumeCompletionRateUseCase {

    /**
     * 이력서의 완성도를 조회하는 UseCase
     *
     * @param resumeId 이력서 ID
     * @return 이력서 완성도 응답 DTO
     */
    ReadUserResumeCompletionRateResponseDto execute(
            UUID resumeId
    );
}
