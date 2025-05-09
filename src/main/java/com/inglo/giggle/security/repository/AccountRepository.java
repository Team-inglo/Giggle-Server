package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccountRepository {

    Account findByIdOrElseThrow(UUID accountId);

    Account findBySerialIdOrElseNull(String serialId);

    Account findBySerialIdAndProviderOrElseThrowUserNameNotFoundException(String serialId, ESecurityProvider provider);

    Account findBySerialIdAndProviderOrElseThrow(String serialId, ESecurityProvider provider);

    Account findByEmailAndProviderOrElseNull(String email, ESecurityProvider provider);

    Account findByEmailOrElseNull(String email);

    List<Account> findAllBeforeEndDate(LocalDateTime endDate);

    List<Account> findAllBeforeEndDateWithDeleted(LocalDateTime endDate);

    void save(Account account);

    Account saveAndReturn(Account account);

    void deleteById(UUID accountId);

    Page<Account> findAccountByFilter(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
    );
}
