package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserEducationDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserEducationDetailUseCase {

    /**
     * (유학생) 학력 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @param educationId 학력 ID
     * @return 학력 상세 조회하기 응답 DTO
     */
    ReadUserEducationDetailResponseDto execute(UUID accountId, Long educationId);

}
