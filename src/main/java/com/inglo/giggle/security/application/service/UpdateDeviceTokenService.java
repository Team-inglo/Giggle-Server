package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.event.dto.DeregisterDeviceTokenEventDto;
import com.inglo.giggle.core.event.dto.UpdateDeviceTokenEventDto;
import com.inglo.giggle.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.inglo.giggle.security.application.usecase.UpdateDeviceTokenUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import com.inglo.giggle.security.domain.service.AccountDeviceService;
import com.inglo.giggle.security.repository.AccountDeviceRepository;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase {

    private final AccountRepository accountRepository;
    private final AccountDeviceRepository accountDeviceRepository;

    private final AccountDeviceService accountDeviceService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateDeviceTokenRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        UUID uuidDeviceId = UUID.fromString(requestDto.deviceId());

        // Device Token 갱신
        // 만약 해당 Account에 해당 DeviceToken이 이미 존재한다면 Device Token을 갱신하고,
        // 존재하지 않는다면 새로운 AccountDevice를 생성한다.
        AccountDevice accountDevice = accountDeviceRepository.findByDeviceIdOrElseNull(uuidDeviceId);

        if (accountDevice != null) {
            // Device Token 삭제 이벤트 발행
            applicationEventPublisher.publishEvent(
                    DeregisterDeviceTokenEventDto.of(
                            accountDevice
                    )
            );

            accountDeviceService.updateAccountDevice(
                    accountDevice,
                    requestDto.deviceToken(),
                    uuidDeviceId
            );

            // Device Token 등록 이벤트 발행
            applicationEventPublisher.publishEvent(
                    UpdateDeviceTokenEventDto.of(
                            accountDevice
                    )
            );
        } else {
            AccountDevice newAccountDevice = accountDeviceService.createAccountDevice(
                    account,
                    requestDto.deviceToken(),
                    uuidDeviceId
            );
            accountDeviceRepository.save(newAccountDevice);

            // Device Token 등록 이벤트 발행
            applicationEventPublisher.publishEvent(
                    UpdateDeviceTokenEventDto.of(
                            newAccountDevice
                    )
            );
        }

        accountRepository.save(account);
    }
}
