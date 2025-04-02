package com.inglo.giggle.security.persistence.repository.impl;

import com.inglo.giggle.security.domain.AccountDevice;
import com.inglo.giggle.security.persistence.entity.mysql.AccountDeviceEntity;
import com.inglo.giggle.security.persistence.mapper.AccountDeviceMapper;
import com.inglo.giggle.security.persistence.repository.AccountDeviceRepository;
import com.inglo.giggle.security.persistence.repository.mysql.AccountDeviceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountDeviceRepositoryImpl implements AccountDeviceRepository {

    private final AccountDeviceJpaRepository accountDeviceJpaRepository;

    @Override
    public List<AccountDevice> findByAccountId(UUID accountId) {
        return AccountDeviceMapper.toDomains(accountDeviceJpaRepository.findByAccountEntityId(accountId));
    }

    @Override
    public AccountDevice save(AccountDevice accountDevice) {
        AccountDeviceEntity entity = AccountDeviceMapper.toEntity(accountDevice);
        return AccountDeviceMapper.toDomain(accountDeviceJpaRepository.save(entity));
    }

    @Override
    public void deleteAllByAccountId(UUID accountId) {
        accountDeviceJpaRepository.deleteAllByAccountEntityId(accountId);
    }
}
