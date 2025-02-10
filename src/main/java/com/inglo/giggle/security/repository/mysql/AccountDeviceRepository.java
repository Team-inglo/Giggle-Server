package com.inglo.giggle.security.repository.mysql;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDeviceRepository extends JpaRepository<AccountDevice, String> {

    Optional<AccountDevice> findByAccountAndDeviceId(
            Account account,
            String deviceId
    );
}
