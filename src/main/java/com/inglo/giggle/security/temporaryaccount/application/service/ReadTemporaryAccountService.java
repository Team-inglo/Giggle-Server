package com.inglo.giggle.security.temporaryaccount.application.service;

import com.inglo.giggle.security.temporaryaccount.application.port.in.query.ReadTemporaryAccountQuery;
import com.inglo.giggle.security.temporaryaccount.application.port.in.result.ReadTemporaryAccountResult;
import com.inglo.giggle.security.temporaryaccount.application.port.out.LoadTemporaryAccountPort;
import com.inglo.giggle.security.temporaryaccount.domain.TemporaryAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadTemporaryAccountService implements ReadTemporaryAccountQuery {

    private final LoadTemporaryAccountPort loadTemporaryAccountPort;

    @Override
    public ReadTemporaryAccountResult execute(String email) {
        TemporaryAccount temporaryAccount = loadTemporaryAccountPort.loadTemporaryAccount(email);
        return ReadTemporaryAccountResult.of(temporaryAccount.getEmail(), temporaryAccount.getPassword(), temporaryAccount.getAccountType());
    }
}
