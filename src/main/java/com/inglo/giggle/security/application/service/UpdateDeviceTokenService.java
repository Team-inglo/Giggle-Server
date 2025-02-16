package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.event.dto.DeregisterDeviceTokenEventDto;
import com.inglo.giggle.core.event.dto.UpdateDeviceTokenEventDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.inglo.giggle.security.application.usecase.UpdateDeviceTokenUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import com.inglo.giggle.security.domain.service.AccountDeviceService;
import com.inglo.giggle.security.repository.mysql.AccountDeviceRepository;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
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
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        UUID uuidDeviceId = UUID.nameUUIDFromBytes(requestDto.deviceToken().getBytes());

        // Device Token 갱신
        // 만약 해당 Account에 해당 DeviceId가 이미 존재한다면 Device Token을 갱신하고,
        // 존재하지 않는다면 새로운 AccountDevice를 생성한다.
        accountDeviceRepository.findByAccountAndDeviceId(account, uuidDeviceId)
                .ifPresentOrElse(
                        accountDevice -> {

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

                            // Device Token 갱신 이벤트 발행
                            applicationEventPublisher.publishEvent(
                                    UpdateDeviceTokenEventDto.of(
                                            accountDevice
                                    )
                            );
                        },
                        () -> {
                            AccountDevice accountDevice = accountDeviceService.createAccountDevice(
                                    account,
                                    requestDto.deviceToken(),
                                    uuidDeviceId
                            );
                            accountDeviceRepository.save(accountDevice);

                            // Device Token 등록 이벤트 발행
                            applicationEventPublisher.publishEvent(
                                    UpdateDeviceTokenEventDto.of(
                                            accountDevice
                                    )
                            );
                        }
                );

        accountRepository.save(account);
    }
}
