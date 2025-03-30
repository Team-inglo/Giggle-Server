package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;

import java.util.List;
import java.util.UUID;

public interface AccountDeviceRepository {

    List<AccountDevice> findByAccountId(UUID accountId);

    AccountDevice findByAccountAndDeviceTokenOrElseNull(Account account, String deviceToken);

    void save(AccountDevice accountDevice);

    void deleteAllByAccountId(UUID accountId);

    AccountDevice findByDeviceIdOrElseNull(UUID uuidDeviceId);
}
