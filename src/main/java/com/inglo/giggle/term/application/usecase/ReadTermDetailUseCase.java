package com.inglo.giggle.term.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.term.application.dto.response.ReadTermDetailResponseDto;

@UseCase
public interface ReadTermDetailUseCase {
    /**
     * 유저의 약관 상세정보 조회하기 유스케이스
     * @param termType 약관 타입
     * @return ReadUserTermDetailResponseDto 유저 약관 상세정보 DTO
     */
    ReadTermDetailResponseDto execute(String termType);
}
