package com.inglo.giggle.user.application.port.in.query;

import com.inglo.giggle.user.application.port.in.result.ReadUserDetailResult;

import java.util.UUID;

public interface ReadUserDetailQuery {

    ReadUserDetailResult execute(UUID accountId);
}
