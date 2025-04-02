package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.response.ReadUserLanguageSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserLanguageSummaryUseCase {

    /**
     * (유학생) 언어 요약 조회하기 유스케이스
     * @param accountId 계정 ID
     * @return 언어 요약 조회하기 응답 DTO
     */
    ReadUserLanguageSummaryResponseDto execute(UUID accountId);
}
