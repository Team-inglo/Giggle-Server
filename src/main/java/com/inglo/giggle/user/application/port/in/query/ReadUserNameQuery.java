package com.inglo.giggle.user.application.port.in.query;

import com.inglo.giggle.user.application.port.in.result.ReadUserNameResult;

import java.util.UUID;

public interface ReadUserNameQuery {

    ReadUserNameResult execute(UUID accountId);
}
