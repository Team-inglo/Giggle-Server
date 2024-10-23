package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.application.usecase.ValidateEmailUseCase;
import com.inglo.giggle.security.application.dto.response.ValidationResponseDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateEmailService implements ValidateEmailUseCase {

    private final AccountRepository accountRepository;

    @Override
    public ValidationResponseDto execute(String email) {
        return ValidationResponseDto.of(isValidateEmail(email));
    }

    /**
     * 중복된 이메일인지 확인
     * @param email 이메일
     * @return 중복된 이메일인지 여부
     */
    private Boolean isValidateEmail(String email) {
        return accountRepository.findByEmailAndProvider(email, ESecurityProvider.DEFAULT).isEmpty();
    }

}
