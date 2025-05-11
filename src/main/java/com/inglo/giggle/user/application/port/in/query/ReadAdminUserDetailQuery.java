package com.inglo.giggle.user.application.port.in.query;

import com.inglo.giggle.user.application.port.in.result.ReadAdminUserDetailResult;

import java.util.UUID;

public interface ReadAdminUserDetailQuery {

    ReadAdminUserDetailResult execute(UUID accountId);
}
