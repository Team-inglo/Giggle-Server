package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.application.port.in.result.ValidationResult;

public interface ValidateEmailQuery {

    /**
     * 이메일 인증 코드 발급
     * @param email 이메일 인증 요청 DTO
     * @return 이메일 인증 응답 DTO
     */
    ValidationResult execute(String email);
}
