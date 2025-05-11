package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.query.ReadAccountDetailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountDetailResult;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.AccountDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAccountDetailService implements ReadAccountDetailQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public ReadAccountDetailResult execute(UUID accountId) {
        return new ReadAccountDetailResult(
                loadAccountPort.loadAccount(accountId).getId(),
                loadAccountPort.loadAccount(accountId).getProvider(),
                loadAccountPort.loadAccount(accountId).getRole(),
                loadAccountPort.loadAccount(accountId).getSerialId(),
                loadAccountPort.loadAccount(accountId).getAccountDevices().stream()
                        .map(AccountDevice::getDeviceToken)
                        .toList()
        );
    }
}
