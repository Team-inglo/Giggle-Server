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
                loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId).getId(),
                loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId).getProvider(),
                loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId).getRole(),
                loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId).getSerialId(),
                loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId).getAccountDevices().stream()
                        .map(AccountDevice::getDeviceToken)
                        .toList()
        );
    }
}
