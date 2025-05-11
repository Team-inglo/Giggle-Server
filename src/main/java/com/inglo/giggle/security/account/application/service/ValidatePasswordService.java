package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.query.ValidatePasswordQuery;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.adapter.in.web.dto.ValidatePasswordRequestDto;
import com.inglo.giggle.security.account.application.port.in.result.ValidatePasswordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidatePasswordService implements ValidatePasswordQuery {

    private final LoadAccountPort loadAccountPort;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ValidatePasswordResponseDto execute(UUID accountId, ValidatePasswordRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        return ValidatePasswordResponseDto.of(
                bCryptPasswordEncoder.matches(
                        requestDto.password(),
                        account.getPassword()
                )
        );
    }
}
