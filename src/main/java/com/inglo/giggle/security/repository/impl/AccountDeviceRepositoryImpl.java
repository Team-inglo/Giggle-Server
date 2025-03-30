package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import com.inglo.giggle.security.repository.AccountDeviceRepository;
import com.inglo.giggle.security.repository.mysql.AccountDeviceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountDeviceRepositoryImpl implements AccountDeviceRepository {

    private final AccountDeviceJpaRepository accountDeviceJpaRepository;

    @Override
    public List<AccountDevice> findByAccountId(UUID accountId) {
        return accountDeviceJpaRepository.findByAccountId(accountId);
    }

    @Override
    public AccountDevice findByAccountAndDeviceTokenOrElseNull(Account account, String deviceToken) {
        return accountDeviceJpaRepository.findByAccountAndDeviceToken(account, deviceToken).orElse(null);
    }

    @Override
    public void save(AccountDevice accountDevice) {
        accountDeviceJpaRepository.save(accountDevice);
    }

    @Override
    public void deleteAllByAccountId(UUID accountId) {
        accountDeviceJpaRepository.deleteAllByAccountId(accountId);
    }

    @Override
    public AccountDevice findByDeviceIdOrElseNull(UUID uuidDeviceId) {
        return accountDeviceJpaRepository.findByDeviceId(uuidDeviceId).orElse(null);
    }
}
