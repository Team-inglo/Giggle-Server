package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.query.ReadAccumulatedAccountQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccumulatedAccountResult;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAccumulatedAccountService implements ReadAccumulatedAccountQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public ReadAccumulatedAccountResult execute(LocalDateTime startDate) {
        List<Account> accumulatedAccounts = loadAccountPort.loadAccounts(startDate);

        List<ReadAccumulatedAccountResult.AccountDto> accountIds = accumulatedAccounts.stream()
                .map(account -> new ReadAccumulatedAccountResult.AccountDto(account.getId(), account.getRole(), account.getCreatedAt()))
                .toList();

        return new ReadAccumulatedAccountResult(accountIds);
    }
}
