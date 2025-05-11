package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;

import java.util.UUID;

public interface ReadAccountRoleQuery {

    ReadAccountRoleResult execute(UUID accountId);
}
