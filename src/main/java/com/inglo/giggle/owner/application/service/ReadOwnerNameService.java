package com.inglo.giggle.owner.application.service;

import com.inglo.giggle.owner.application.port.in.query.ReadOwnerNameQuery;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerNameResult;
import com.inglo.giggle.owner.application.port.out.LoadOwnerPort;
import com.inglo.giggle.owner.domain.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerNameService implements ReadOwnerNameQuery {

    private final LoadOwnerPort loadOwnerPort;

    @Override
    public ReadOwnerNameResult execute(UUID accountId) {
        Owner owner = loadOwnerPort.loadOwnerOrElseNull(accountId);
        return owner != null ? new ReadOwnerNameResult(owner.getName()) : null;
    }
}
