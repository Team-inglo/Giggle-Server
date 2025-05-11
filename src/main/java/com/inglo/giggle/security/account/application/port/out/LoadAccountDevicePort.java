package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.AccountDevice;

import java.util.List;
import java.util.UUID;

public interface LoadAccountDevicePort {

    List<AccountDevice> loadAccountDevices(UUID accountId);
}
