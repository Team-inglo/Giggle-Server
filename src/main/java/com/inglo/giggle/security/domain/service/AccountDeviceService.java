package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountDeviceService {

    public void updateAccountDevice(
            AccountDevice accountDevice,
            String deviceToken,
            UUID deviceId
    ) {
        accountDevice.updateDeviceToken(deviceToken);
        accountDevice.updateDeviceId(deviceId);
    }

    public AccountDevice createAccountDevice(
            Account account,
            String deviceToken,
            UUID deviceId
    ) {
        return AccountDevice.builder()
                .account(account)
                .deviceToken(deviceToken)
                .deviceId(deviceId)
                .build();
    }
}
