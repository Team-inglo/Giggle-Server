package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAccountRoleService implements ReadAccountRoleQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public ReadAccountRoleResult execute(UUID accountId) {
        return new ReadAccountRoleResult(loadAccountPort.loadAccount(accountId).getRole());
    }
}
