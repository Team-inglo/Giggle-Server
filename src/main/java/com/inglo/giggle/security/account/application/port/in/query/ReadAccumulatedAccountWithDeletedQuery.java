package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.application.port.in.result.ReadAccumulatedAccountWithDeletedResult;

import java.time.LocalDateTime;

public interface ReadAccumulatedAccountWithDeletedQuery {

    ReadAccumulatedAccountWithDeletedResult execute(LocalDateTime startDate);
}
