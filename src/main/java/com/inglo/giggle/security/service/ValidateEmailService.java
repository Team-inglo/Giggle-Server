package com.inglo.giggle.security.service;

import com.inglo.giggle.security.dto.response.ValidationResponseDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.usecase.ValidateEmailUseCase;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateEmailService implements ValidateEmailUseCase {

    private final AccountRepository accountRepository;

    @Override
    public ValidationResponseDto execute(String email) {
        return ValidationResponseDto.builder()
                .isValid(isDuplicatedEmail(email))
                .build();
    }

    /**
     * 중복된 이메일인지 확인
     * @param email 이메일
     * @return 중복된 이메일인지 여부
     */
    private Boolean isDuplicatedEmail(String email) {
        return accountRepository.findBySerialIdAndProvider(email, ESecurityProvider.DEFAULT).isPresent();
    }

}
