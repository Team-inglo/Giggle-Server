package com.inglo.giggle.term.application.port.in.query;

import com.inglo.giggle.term.application.port.in.result.ReadTermDetailResult;

public interface ReadTermDetailQuery {
    /**
     * 유저의 약관 상세정보 조회하기 유스케이스
     * @param termType 약관 타입
     * @return ReadUserTermDetailResponseDto 유저 약관 상세정보 DTO
     */
    ReadTermDetailResult execute(String termType);
}
