package com.inglo.giggle.owner.application.port.in.query;

import com.inglo.giggle.owner.application.port.in.result.ReadOwnerNameResult;

import java.util.UUID;

public interface ReadOwnerNameQuery {

    ReadOwnerNameResult execute(UUID accountId);
}
