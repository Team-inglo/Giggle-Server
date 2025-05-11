package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerNameQuery;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerNameResult;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountBriefResult;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountBriefQuery;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.user.application.port.in.query.ReadUserNameQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserNameResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAccountBriefService implements ReadAccountBriefQuery {

    private final LoadAccountPort loadAccountPort;
    private final ReadUserNameQuery readUserNameQuery;
    private final ReadOwnerNameQuery readOwnerNameQuery;

    @Override
    public ReadAccountBriefResult execute(UUID accountId) {
        Account account = loadAccountPort.loadAccount(accountId);
        ReadUserNameResult readUserNameResult = readUserNameQuery.execute(accountId);
        ReadOwnerNameResult readOwnerNameResult = readOwnerNameQuery.execute(accountId);

        // 어드민인 경우 예외처리
        if (readUserNameResult == null && readOwnerNameResult == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_ACCOUNT);
        }

        return ReadAccountBriefResult.builder()
                .accountType(account.getRole().toString())
                .name(readUserNameResult != null ? readUserNameResult.getName() : readOwnerNameResult.getName())
                .build();
    }
}
