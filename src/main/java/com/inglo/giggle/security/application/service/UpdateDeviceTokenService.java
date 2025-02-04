package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.inglo.giggle.security.application.usecase.UpdateDeviceTokenUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase {

    private final AccountRepository accountRepository;

    private final AccountService accountService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateDeviceTokenRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // Device Token 갱신
        account = accountService.updateDeviceToken(account, requestDto.deviceToken());

        accountRepository.save(account);
    }
}
