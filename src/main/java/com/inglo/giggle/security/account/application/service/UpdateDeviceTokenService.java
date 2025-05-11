package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.command.UpdateDeviceTokenCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.UpdateDeviceTokenUseCase;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.application.port.out.UpdateAccountDevicePort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.AccountDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountDevicePort updateAccountDevicePort;

    @Override
    @Transactional
    public void execute(UpdateDeviceTokenCommand command) {

        Account account = loadAccountPort.loadAccountWithAccountDevices(command.getAccountId());
        UUID uuidDeviceId = UUID.fromString(command.getDeviceId());

        // Device Token 갱신
        // 만약 해당 Account에 해당 DeviceToken이 이미 존재한다면 Device Token을 갱신하고,
        // 존재하지 않는다면 새로운 AccountDevice를 생성한다.
        AccountDevice accountDevice = account.getAccountDevices().stream()
                .filter(device -> device.getDeviceId().equals(uuidDeviceId))
                .findFirst()
                .orElse(null);

        if (accountDevice != null) {
            accountDevice.updateSelf(uuidDeviceId, command.getDeviceToken());
        } else {
            accountDevice = AccountDevice.builder()
                    .deviceToken(command.getDeviceToken())
                    .deviceId(uuidDeviceId)
                    .build();
            account.getAccountDevices().add(accountDevice);
        }
        updateAccountDevicePort.updateAccountDevice(account);
    }
}
