package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.inglo.giggle.security.application.usecase.UpdateDeviceTokenUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountDeviceService;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountDeviceRepository;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase {

    private final AccountRepository accountRepository;
    private final AccountDeviceRepository accountDeviceRepository;

    private final AccountDeviceService accountDeviceService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateDeviceTokenRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // Device Token 갱신
        // 만약 해당 Account에 해당 DeviceId가 이미 존재한다면 Device Token을 갱신하고,
        // 존재하지 않는다면 새로운 AccountDevice를 생성한다.
        accountDeviceRepository.findByAccountAndDeviceId(account, requestDto.deviceId())
                .ifPresentOrElse(
                        accountDevice -> accountDeviceService.updateDeviceToken(
                                accountDevice,
                                requestDto.deviceToken()
                        ),
                        () -> accountDeviceRepository.save(
                                accountDeviceService.createAccountDevice(
                                        account,
                                        requestDto.deviceId(),
                                        requestDto.deviceToken()
                                )
                        )
                );

        accountRepository.save(account);
    }
}
