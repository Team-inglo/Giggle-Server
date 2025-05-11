package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.AccountDevice;

public interface CreateAccountDevicePort {

    void createAccountDevice(AccountDevice accountDevice);
}
