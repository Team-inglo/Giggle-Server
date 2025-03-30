package com.inglo.giggle.security.repository.mysql;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDeviceJpaRepository extends JpaRepository<AccountDevice, String> {

    List<AccountDevice> findByAccountId(UUID accountId);

    Optional<AccountDevice> findByAccountAndDeviceToken(Account account, String deviceToken);

    void deleteAllByAccountId(UUID accountId);

    Optional<AccountDevice> findByDeviceId(UUID uuidDeviceId);
}
