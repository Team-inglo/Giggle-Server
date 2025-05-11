package com.inglo.giggle.security.temporaryaccount.application.port.in.query;

import com.inglo.giggle.security.temporaryaccount.application.port.in.result.ReadTemporaryAccountResult;

public interface ReadTemporaryAccountQuery {
    /**
     * 임시 계정 조회
     *
     * @param email 임시 계정 이메일
     * @return 임시 계정 정보
     */
    ReadTemporaryAccountResult execute(String email);
}
