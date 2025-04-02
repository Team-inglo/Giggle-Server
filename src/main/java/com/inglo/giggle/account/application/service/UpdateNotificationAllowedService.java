package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.usecase.UpdateNotificationAllowedUseCase;
import com.inglo.giggle.account.presentation.dto.request.UpdateNotificationAllowedRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNotificationAllowedService implements UpdateNotificationAllowedUseCase {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateNotificationAllowedRequestDto requestDto) {

        // 계정 정보 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 알림 허용 여부 업데이트
        account.updateNotificationAllowed(requestDto.isNotificationAllowed());
        accountRepository.save(account);
    }

}
