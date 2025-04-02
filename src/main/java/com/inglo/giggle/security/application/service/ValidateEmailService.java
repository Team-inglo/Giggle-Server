package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.presentation.dto.response.ValidationResponseDto;
import com.inglo.giggle.security.application.usecase.ValidateEmailUseCase;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
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
        return accountRepository.findByEmailAndProviderOrElseNull(email, ESecurityProvider.DEFAULT) == null;
    }

}
