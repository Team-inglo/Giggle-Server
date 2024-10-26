package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.request.UpdateNotificationAllowedRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateNotificationAllowedUseCase;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNotificationAllowedService implements UpdateNotificationAllowedUseCase {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateNotificationAllowedRequestDto requestDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        account = accountService.updateNotificationAllowed(account, requestDto.isNotificationAllowed());
        accountRepository.save(account);
    }
}
