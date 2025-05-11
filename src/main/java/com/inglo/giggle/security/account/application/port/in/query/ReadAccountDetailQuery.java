package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.application.port.in.result.ReadAccountDetailResult;

import java.util.UUID;

public interface ReadAccountDetailQuery {

    ReadAccountDetailResult execute(UUID accountId);
}
