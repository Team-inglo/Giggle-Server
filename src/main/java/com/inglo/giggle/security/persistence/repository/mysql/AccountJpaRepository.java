package com.inglo.giggle.security.persistence.repository.mysql;

import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT a FROM AccountEntity a LEFT JOIN FETCH a.accountDeviceEntities WHERE a.id = :accountId")
    Optional<AccountEntity> findByIdWithAccountDevices(@Param("accountId") UUID accountId);

    Optional<AccountEntity> findBySerialId(String serialId);

    Optional<AccountEntity> findBySerialIdAndProvider(String serialId, ESecurityProvider provider);

    Optional<AccountEntity> findByEmailAndProvider(String email, ESecurityProvider provider);

    Optional<AccountEntity> findByEmail(String email);

    @Query("SELECT a FROM AccountEntity a WHERE a.createdAt <= :endDate")
    List<AccountEntity> findAllBeforeEndDate(@Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM accounts a LEFT JOIN users u ON a.id = u.account_id LEFT JOIN owners o ON a.id = o.account_id WHERE created_at <= :endDate", nativeQuery = true)
    List<AccountEntity> findAllBeforeEndDateWithDeleted(@Param("endDate") LocalDateTime endDate);
}
