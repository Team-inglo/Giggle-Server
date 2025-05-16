package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkPreferenceDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserWorkPreferenceDetailUseCase {

    /**
     * 7.21 (유학생) 희망 근로 조건 상세 조회하기
     */
    ReadUserWorkPreferenceDetailResponseDto execute(UUID accountId);
}
