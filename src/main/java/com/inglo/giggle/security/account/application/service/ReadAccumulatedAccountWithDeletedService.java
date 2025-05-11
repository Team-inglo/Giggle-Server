package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.query.ReadAccumulatedAccountWithDeletedQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccumulatedAccountWithDeletedResult;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAccumulatedAccountWithDeletedService implements ReadAccumulatedAccountWithDeletedQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public ReadAccumulatedAccountWithDeletedResult execute(LocalDateTime startDate) {
        List<Account> accumulatedAccounts = loadAccountPort.loadAccountsWithDeleted(startDate);

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> accountIds = accumulatedAccounts.stream()
                .map(account -> new ReadAccumulatedAccountWithDeletedResult.AccountDto(account.getId(), account.getRole(), account.getCreatedAt(), account.getDeletedAt()))
                .toList();

        return new ReadAccumulatedAccountWithDeletedResult(accountIds);
    }
}
