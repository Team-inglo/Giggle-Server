package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.presentation.dto.response.ReadUserSchoolDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserSchoolDetailUseCase {

    /**
     * 유저 학교 정보 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @return ReadUserSchoolDetailResponseDto 유저 학교 상세정보 DTO
     */
    ReadUserSchoolDetailResponseDto execute(UUID accountId);
}
