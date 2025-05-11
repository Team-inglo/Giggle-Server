package com.inglo.giggle.security.account.application.port.out;

import java.util.UUID;

public interface DeleteAccountDevicePort {

    void deleteAccountDevices(UUID accountId);
}
