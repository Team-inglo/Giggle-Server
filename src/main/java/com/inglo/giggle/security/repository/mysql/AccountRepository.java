package com.inglo.giggle.security.repository.mysql;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.repository.mysql.querydsl.AccountRepositoryQuery;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, AccountRepositoryQuery {

    Optional<Account> findBySerialId(String serialId);

    Optional<Account> findBySerialIdAndProvider(String serialId, ESecurityProvider provider);

    Optional<Account> findByEmailAndProvider(String email, ESecurityProvider provider);

    Optional<Account> findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.createdAt <= :endDate")
    List<Account> findAllBeforeEndDate(@Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM accounts a LEFT JOIN users u ON a.id = u.account_id LEFT JOIN owners o ON a.id = o.account_id WHERE created_at <= :endDate", nativeQuery = true)
    List<Account> findAllBeforeEndDateWithDeleted(@Param("endDate") LocalDateTime endDate);
}
