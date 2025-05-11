package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.application.port.in.result.ReadAccumulatedAccountResult;

import java.time.LocalDateTime;

public interface ReadAccumulatedAccountQuery {

    ReadAccumulatedAccountResult execute(LocalDateTime startDate);
}
