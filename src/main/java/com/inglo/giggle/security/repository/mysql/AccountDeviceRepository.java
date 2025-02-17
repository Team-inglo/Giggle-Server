package com.inglo.giggle.security.repository.mysql;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDeviceRepository extends JpaRepository<AccountDevice, String> {

    Optional<AccountDevice> findByAccountAndDeviceId(
            Account account,
            UUID deviceId
    );

    List<AccountDevice> findByAccount(Account account);

    List<AccountDevice> findByAccountId(UUID accountId);

    Optional<AccountDevice> findByAccountAndDeviceToken(Account account, String deviceToken);
}
