package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.query.ValidateEmailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ValidationResult;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateEmailService implements ValidateEmailQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public ValidationResult execute(String email) {
        return ValidationResult.of(isValidateEmail(email));
    }

    /**
     * 중복된 이메일인지 확인
     * @param email 이메일
     * @return 중복된 이메일인지 여부
     */
    private Boolean isValidateEmail(String email) {
        return loadAccountPort.loadAccount(email, ESecurityProvider.DEFAULT) == null;
    }

}
