package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LoadAccountPort {

    Account loadAllAccountOrElseThrow(UUID accountId);

    Account loadAccountWithRefreshTokenOrElseThrow(UUID accountId);

    Account loadAccountWithAccountDevicesOrElseThrow(UUID accountId);

    Account loadAccountOrElseThrowUserNameNotFoundException(String serialId, ESecurityProvider provider);

    Account loadAccountOrElseThrow(String serialId, ESecurityProvider provider);

    List<Account> loadAccounts(LocalDateTime endDate);

    List<Account> loadAccountsWithDeleted(LocalDateTime endDate);

    Page<Account> loadAccounts(
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
