package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.presentation.dto.response.ValidationResponseDto;
import com.inglo.giggle.security.application.usecase.ValidateIdUseCase;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateIdService implements ValidateIdUseCase {

    private final AccountRepository accountRepository;

    @Override
    public ValidationResponseDto execute(String id) {
        return ValidationResponseDto.of(isValidateId(id));
    }

    /**
     * 중복된 아이디인지 확인
     * @param id id
     * @return 중복된 아이디인지 여부
     */
    private Boolean isValidateId(String id) {
        return accountRepository.findByEmailOrElseNull(id) == null;
    }
}
