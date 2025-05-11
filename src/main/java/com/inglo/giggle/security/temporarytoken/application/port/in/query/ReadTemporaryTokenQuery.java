package com.inglo.giggle.security.temporarytoken.application.port.in.query;

import com.inglo.giggle.security.temporarytoken.application.port.in.result.ReadTemporaryTokenResult;

public interface ReadTemporaryTokenQuery {
    /**
     * 임시 토큰 조회
     *
     * @param value 임시 토큰
     * @return 임시 토큰 조회 결과
     */
    ReadTemporaryTokenResult execute(String value);
}
