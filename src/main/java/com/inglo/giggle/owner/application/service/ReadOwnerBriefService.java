package com.inglo.giggle.owner.application.service;

import com.inglo.giggle.owner.domain.Owner;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerBriefResult;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerBriefQuery;
import com.inglo.giggle.owner.application.port.out.LoadOwnerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerBriefService implements ReadOwnerBriefQuery {

    private final LoadOwnerPort loadOwnerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerBriefResult execute(UUID accountId) {

        // 고용주 정보 조회
        Owner owner = loadOwnerPort.loadOwner(accountId);

        return ReadOwnerBriefResult.of(
                owner.getProfileImgUrl(),
                owner.getCompanyName(),
                owner.getNotificationAllowed()
        );
    }

}
