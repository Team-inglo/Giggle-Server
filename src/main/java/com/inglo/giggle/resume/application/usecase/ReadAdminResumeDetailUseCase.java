package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.response.ReadAdminResumeDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadAdminResumeDetailUseCase {
    /**
     * 7.20 (어드민) 이력서 조회하기
     */
    ReadAdminResumeDetailResponseDto execute(UUID resumeId);
}
