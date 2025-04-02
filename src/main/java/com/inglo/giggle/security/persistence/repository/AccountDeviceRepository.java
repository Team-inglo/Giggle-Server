package com.inglo.giggle.security.persistence.repository;

import com.inglo.giggle.security.domain.AccountDevice;

import java.util.List;
import java.util.UUID;

public interface AccountDeviceRepository {

    List<AccountDevice> findByAccountId(UUID accountId);

    AccountDevice save(AccountDevice accountDevice);

    void deleteAllByAccountId(UUID accountId);
}
