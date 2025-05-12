package com.inglo.giggle.security.account.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {

    @EntityGraph(attributePaths = {"accountDevices"})
    Optional<AccountEntity> findWithAccountDevicesById(UUID id);

    Optional<AccountEntity> findBySerialIdAndProvider(String serialId, ESecurityProvider provider);

    @Query("SELECT a FROM AccountEntity a WHERE a.createdAt <= :endDate")
    List<AccountEntity> findAllBeforeEndDate(@Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM accounts a LEFT JOIN users u ON a.id = u.account_id LEFT JOIN owners o ON a.id = o.account_id WHERE created_at <= :endDate", nativeQuery = true)
    List<AccountEntity> findAllBeforeEndDateWithDeleted(@Param("endDate") LocalDateTime endDate);
}
