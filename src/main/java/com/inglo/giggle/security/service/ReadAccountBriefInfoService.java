package com.inglo.giggle.security.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.dto.response.AccountBriefInfoResponseDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.usecase.ReadAccountBriefInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAccountBriefInfoService implements ReadAccountBriefInfoUseCase {
    private final AccountRepository accountRepository;

    @Override
    public AccountBriefInfoResponseDto execute(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        return AccountBriefInfoResponseDto.of(account);
    }
}
