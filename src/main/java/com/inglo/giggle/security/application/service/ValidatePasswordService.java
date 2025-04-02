package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.application.usecase.ValidatePasswordUseCase;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import com.inglo.giggle.security.presentation.dto.request.ValidatePasswordRequestDto;
import com.inglo.giggle.security.presentation.dto.response.ValidatePasswordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidatePasswordService implements ValidatePasswordUseCase {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ValidatePasswordResponseDto execute(UUID accountId, ValidatePasswordRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        return ValidatePasswordResponseDto.of(
                bCryptPasswordEncoder.matches(
                        requestDto.password(),
                        account.getPassword()
                )
        );
    }
}
