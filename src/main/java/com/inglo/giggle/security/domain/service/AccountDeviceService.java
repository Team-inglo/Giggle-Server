package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import org.springframework.stereotype.Service;

@Service
public class AccountDeviceService {

    public void updateDeviceToken(AccountDevice accountDevice, String deviceToken) {
        accountDevice.updateDeviceToken(deviceToken);
    }

    public AccountDevice createAccountDevice(
            Account account,
            String deviceId,
            String deviceToken
    ) {
        return AccountDevice.builder()
                .account(account)
                .deviceId(deviceId)
                .deviceToken(deviceToken)
                .build();
    }
}
