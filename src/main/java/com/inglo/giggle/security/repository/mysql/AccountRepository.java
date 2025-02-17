package com.inglo.giggle.security.repository.mysql;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findBySerialId(String serialId);

    Optional<Account> findBySerialIdAndProvider(String serialId, ESecurityProvider provider);

    Optional<Account> findByEmailAndProvider(String email, ESecurityProvider provider);

    Optional<Account> findByEmail(String email);
}
