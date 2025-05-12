package com.inglo.giggle.security.account.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountDeviceJpaRepository extends JpaRepository<AccountDeviceEntity, String> {

    List<AccountDeviceEntity> findByAccountEntityId(UUID accountId);
}
